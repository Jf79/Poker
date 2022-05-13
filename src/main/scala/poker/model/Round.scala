package poker
package model

import CardsObject._
import util._

case class Round(var player: Player, var deck: Array[Card], var bet: Option[Int], 
  var hand: Option[Array[Card]], var riskType: Option[RiskType]) extends Stateable:
  
  // stateable

  var state = StartState(this)

  override def handle(event: Event): State =
    event match {
      case bet: BetEvent         => state = BetState(this)
      case start: StartEvent     => state = StartState(this)
      case replace: ReplaceEvent => state = ReplaceState(this)
    }
    state
  
  // riskType state

  def setRiskType(risk: String): Round =
    riskType = Some(RiskType(risk))
    this
  
  // bet state
  
  def setBet(b: Int): Round = 
    bet = Some(riskType.get.setBet(b))
    this
  
  // cards state

  def dealCards(): Round =
    val tuple = getRandomCards(deck, 5)
    hand = Some(tuple._1);
    deck = tuple._2
    this
  
  // replace state 

  def holdCards(holdedCards: Vector[Int]): Round =
    val cards = getRandomCards(deck, 5 - holdedCards.length)._1
    hand = Some(replaceCards(holdedCards, cards, hand.get))
    this

  def replaceCards(holdedCards: Vector[Int], cards: Array[Card], 
    hand: Array[Card]): Array[Card] =
    var i = 0
    val newHand = hand.clone
    for (c <- 1 to 5 if (!holdedCards.contains(c)))
      newHand(c - 1) = cards(i); i += 1
    newHand

  // toString

  override def toString =
    state.toString()

