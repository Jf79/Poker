package poker
package controller

import util.Observable
import util.Event
import model.Round
import model.Card
import model.CardsObject._
import model.Player
import model.State

case class Controller(val player: Player, var round: Option[Round])
    extends Observable:

  def handle(event: Event): Option[State] =
    round.get.handle(event)

  def doAndPublish(doThis: Int => Round, bet: Int): Unit =
    round = Some(doThis(bet))
    notifyObservers

  def doAndPublish(createR: Array[Card] => Round, deck: Array[Card]): Unit =
    round = Some(createR(deck))
    notifyObservers
  
  def doAndPublish(doThis: => Round): Unit =
    round = Some(doThis)
    notifyObservers

  def doAndPublish(doThis: Vector[Int] => Round, holdedCards: Vector[Int]): Unit =
    round = Some(doThis(holdedCards))
    notifyObservers
  
  def createRound(deck: Array[Card]): Round =
    round = Some(new Round(player, deck, None, None))
    round.get

  def setBet(bet: Int) : Round = 
    round = Some(round.get.setBet(bet))
    round.get
  
  def start(): Round =
    round = Some(round.get.start())
    round.get

  def holdCards(holdedCards: Vector[Int]): Round =
    round = Some(round.get.holdCards(holdedCards))
    round.get

  def createDeck(): Array[Card] =
    createCards()

  override def toString =
    round.get.toString
