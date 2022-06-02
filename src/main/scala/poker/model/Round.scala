package poker
package model

import CardsObject._
import CombinationObject._
import util._

import scala.util.{Try, Success, Failure}


case class Round(player: Player, var deck: Array[Card]) extends Stateable:

  var bet: Option[Int] = None
  var hand: Option[Array[Card]] = None
  var riskType: Option[RiskType] = None
  var updateMessage: String = ""
  var combination: Option[Combination] = None
  var combinationHand: Option[Array[Card]] = None
  var outcome: Int = 0

  // stateable
  var state = StartState(this)

  def copyRound() : Round = 
    val copiedRound = new Round(new Player(player.money), deck.clone)
    copiedRound.bet = returnCopy(bet)
    copiedRound.hand = returnCopy(hand)
    copiedRound.riskType = returnCopy(riskType)
    copiedRound.state = State(state.toString, copiedRound)
    copiedRound.updateMessage = new String(updateMessage)
    copiedRound

  def returnCopy[T](arg: Option[T]): Option[T] = if(arg.isEmpty) return None else return Some(arg.get)

  override def handle(event: Event): State =
    event match {
      case risk: RiskTypeEvent  => state = RiskTypeState(this)
      case bet: BetEvent  => state = BetState(this)
      case deal: DealCardsEvent => state = DealCardsState(this)
      case replace: HoldCardsEvent  => state = HoldCardsState(this)
      case end: EvaluationEvent  => state = EvaluationState(this)
    }
    state
  
  // riskType state
  
  def setRiskType(risk: String): Try[Round] =
    val riskTypeTry = Try {RiskType(risk, player.money)}
    handleTryRiskType(riskTypeTry)

  def handleTryRiskType(rType : Try[RiskType]): Try[Round] = 
    var roundTry: Try[Round] = Success(this)
    if(rType.isSuccess) riskType = Some(rType.get)
    else roundTry = Failure(rType.failed.get)
    roundTry

  // bet state 

  def setBet(b: Int): Try[Round] = 
    val betTry = Try{riskType.get.setBet(b, player.money)}
    handleTryBet(betTry)
  
  def handleTryBet(bType : Try[Int]) : Try[Round] = 
    var roundTry: Try[Round] = Success(this)
    if(bType.isSuccess) bet = Some(bType.get)
    else roundTry = Failure(bType.failed.get)
    roundTry

  // cards state

  def dealCards(): Round =
    val tuple = getRandomCards(deck, 5)
    hand = Some(tuple._1); deck = tuple._2
    this
  
  // replace state 

  def holdCards(holdedCards: Vector[Int]): Try[Round] =
    val cards = getRandomCards(deck, 5 - holdedCards.length)._1
    hand = Some(replaceCards(holdedCards, cards, hand.get))
    Success(this)

  def replaceCards(holdedCards: Vector[Int], cards: Array[Card], hand: Array[Card]): Array[Card] =
    var i = 0
    val newHand = hand.clone
    for (c <- 1 to 5 if (!holdedCards.contains(c)))
      newHand(c - 1) = cards(i); i += 1
    newHand

  // evaluation state

  def evaluation(): Round = 
    val comb = checkCombination()
    outcome = comb.get.getMultFactor * bet.get
    player.money = player.money + outcome
    this
    

  def checkCombination(): Option[Combination] =
    val tuple = findCombination(hand.get)
    combination = tuple._1
    combinationHand = filterCombination(tuple)
    combination
        
  def filterCombination(tuple: (Option[Combination], Option[Array[Card]])) : Option[Array[Card]]=
    if(!tuple._1.equals(Combination.NOTHING) && !tuple._2.isEmpty)
      val leftCards = tuple._2.get
      return Some(hand.get.filterNot(leftCards.contains(_)))
    None
  
  def hasEnoughCredit() : Boolean =  player.money > 0

  override def toString = updateMessage

