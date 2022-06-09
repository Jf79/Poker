package poker
package model
package round

import util.CardsObject._
import util.CombinationObject._
import util._
import model.player.CreatePlayer
import player.PlayerInterface
import card.CardInterface

import scala.util.{Try, Success, Failure}


case class Round(player: PlayerInterface, var deck: Array[CardInterface]) extends RoundInterface:

  var bet: Option[Int] = None
  var hand: Option[Array[CardInterface]] = None
  var updateMessage: String = ""
  var combinationHand: Option[Array[CardInterface]] = None
  var outcome: Int = 0

  // stateable
  var state = StartState(this)

  override def copyRound() : RoundInterface = 
    val copiedRound = new Round(player, deck.clone)
    copiedRound.bet = returnCopy(bet)
    copiedRound.hand = returnCopy(hand)
    copiedRound.riskType = returnCopy(riskType)
    copiedRound.state = State(state.toString, copiedRound)
    copiedRound.updateMessage = new String(updateMessage)
    copiedRound.combination = returnCopy(combination)
    copiedRound.combinationHand = returnCopy(combinationHand)
    copiedRound

  def returnCopy[T](arg: Option[T]): Option[T] = if(arg.isEmpty) return None else return Some(arg.get)

  def handle(event: Event): State =
    event match {
      case risk: RiskTypeEvent  => state = RiskTypeState(this)
      case bet: BetEvent  => state = BetState(this)
      case deal: DealCardsEvent => state = DealCardsState(this)
      case replace: HoldCardsEvent  => state = HoldCardsState(this)
      case evaluation: EvaluationEvent  => state = EvaluationState(this)
      case end: EndEvent  => state = EndState(this)
    }
    state
  
  // riskType state
  
  override def setRiskType(risk: String): Try[RoundInterface] =
    val riskTypeTry = Try {RiskType(risk, player.getMoney())}
    handleTryRiskType(riskTypeTry)

  def handleTryRiskType(rType : Try[RiskType]): Try[Round] = 
    var roundTry: Try[Round] = Success(this)
    if(rType.isSuccess) riskType = Some(rType.get)
    else roundTry = Failure(rType.failed.get)
    roundTry

  // bet state 

  override def setBet(b: Int): Try[RoundInterface] = 
    val betTry = Try{riskType.get.setBet(b, player.getMoney())}
    handleTryBet(betTry)
  
  def handleTryBet(bType : Try[Int]) : Try[Round] = 
    var roundTry: Try[Round] = Success(this)
    if(bType.isSuccess) bet = Some(bType.get)
    else roundTry = Failure(bType.failed.get)
    roundTry

  // cards state

  override def dealCards(): RoundInterface =
    val tuple = getRandomCards(deck, 5)
    hand = Some(tuple._1); deck = tuple._2
    this
  
  // replace state 

  override def holdCards(holdedCards: Vector[Int]): Try[RoundInterface] =
    val cards = getRandomCards(deck, 5 - holdedCards.length)._1
    hand = Some(replaceCards(holdedCards, cards, hand.get))
    Success(this)

  def replaceCards(holdedCards: Vector[Int], cards: Array[CardInterface], hand: Array[CardInterface]): Array[CardInterface] =
    var i = 0
    val newHand = hand.clone
    for (c <- 1 to 5 if (!holdedCards.contains(c)))
      newHand(c - 1) = cards(i); i += 1
    newHand

  // evaluation state

  override def evaluation(): Round = 
    val comb = checkCombination(hand.get)
    combination = comb._1
    combinationHand = comb._2
    outcome = comb._1.get.getMultFactor * bet.get
    player.addMoney(outcome)
    this
    

  def checkCombination(hand: Array[CardInterface]): (Option[Combination], Option[Array[CardInterface]]) =
    val tuple = findCombination(hand)
    (tuple._1, filterCombination(tuple, Some(hand)))
        
  def filterCombination(tuple: (Option[Combination], Option[Array[CardInterface]]), hand: Option[Array[CardInterface]]) : Option[Array[CardInterface]]=
    if(!tuple._1.get.equals(Combination.NOTHING))
      var leftCards: Option[Array[CardInterface]] = None
      if(!tuple._2.isEmpty)
        leftCards = tuple._2
      else
        leftCards = Some(Array())
      return Some(hand.get.filterNot(leftCards.get.contains(_)))
    None
  
  override def hasEnoughCredit(): Boolean =  player.getMoney() > 0
  
  override def getHandOfPlayer(): Array[CardInterface] = hand.get 

  override def getCombinationHand(): Option[Array[CardInterface]] = combinationHand

  override def getOutcome(): Int = outcome

  override def toString = updateMessage

