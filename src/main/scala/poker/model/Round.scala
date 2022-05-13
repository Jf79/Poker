package poker
package model

import CardsObject._
import util._

case class Round(var player: Player, var deck: Array[Card], var bet: Option[Int],
  var hand: Option[Array[Card]], gameType: Type) 
  extends Stateable:

  override def handle(event: Event): Option[State] =
    event match {
      case bet: BetEvent         => state = Some(BetState(this))
      case start: StartEvent     => state = Some(StartState(this))
      case replace: ReplaceEvent => state = Some(ReplaceState(this))
    }
    state

  def setBet(b: Int): Round = 
    if(gameType.equals(HighRisk()) && b < 50)
      bet = Some(50)
    else
      bet = Some(b)
    this
  
  def start(): Round =
    val tuple = getRandomCards(deck, 5)
    hand = Some(tuple._1);
    deck = tuple._2
    this

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

  override def toString =
    state.get.toString()

