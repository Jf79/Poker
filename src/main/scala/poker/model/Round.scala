package poker
package model

import CardsObject._
import util._

case class Round(player: Player, var deck: Array[Card]) extends Stateable:

  var bet: Option[Int] = None
  var hand: Option[Array[Card]] = None
  var riskType: Option[RiskType] = None
  var updateMessage: String = ""
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

  def returnCopy[T](arg: Option[T]): Option[T] = if(arg.isEmpty) return None else return arg

  override def handle(event: Event): State =
    event match {
      case risk: RiskTypeEvent  => state = RiskTypeState(this)
      case bet: BetEvent  => state = BetState(this)
      case deal: DealCardsEvent => state = DealCardsState(this)
      case replace: HoldCardsEvent  => state = HoldCardsState(this)
      case end: EndEvent  => state = EndState(this)
    }
    state
  
  // riskType state

  def setRiskType(risk: String): Round =
    riskType = Some(RiskType(risk, player.money))
    this
  
  // bet state
  
  def setBet(b: Int): Round = 
    bet = Some(riskType.get.setBet(b, player.money))
    this
  
  // cards state

  def dealCards(): Round =
    val tuple = getRandomCards(deck, 5)
    hand = Some(tuple._1); deck = tuple._2
    this
  
  // replace state 

  def holdCards(holdedCards: Vector[Int]): Round =
    val cards = getRandomCards(deck, 5 - holdedCards.length)._1
    hand = Some(replaceCards(holdedCards, cards, hand.get))
    this

  def replaceCards(holdedCards: Vector[Int], cards: Array[Card], hand: Array[Card]): Array[Card] =
    var i = 0
    val newHand = hand.clone
    for (c <- 1 to 5 if (!holdedCards.contains(c)))
      newHand(c - 1) = cards(i); i += 1
    newHand
  
  def hasEnoughCredit() : Boolean =  player.money > 0

  override def toString = updateMessage

