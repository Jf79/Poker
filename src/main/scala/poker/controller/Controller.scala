package poker
package controller

import util.Observable
import util.Event
import model.HighRisk
import model.Round
import model.Card
import model.CardsObject._
import model.Player
import model.State
import model.Type

case class Controller(val player: Player, var round: Option[Round])
    extends Observable:

  def handle(event: Event): Option[State] =
    round.get.handle(event)

  def doAndPublish(setBet: Int => Round, bet: Int): Unit =
    round = Some(setBet(bet))
    notifyObservers

  def doAndPublish(createR: (Array[Card], String) => Round, deck: Array[Card], gameType: String): Unit =
    round = Some(createR(deck, gameType))
   // notifyObservers
  
  def doAndPublish(start: => Round): Unit =
    round = Some(start)
    notifyObservers

  def doAndPublish(hold: Vector[Int] => Round, holdedCards: Vector[Int]): Unit =
    round = Some(hold(holdedCards))
    notifyObservers
  
  def createRound(deck: Array[Card], gameType: String): Round =
    round = Some(new Round(player, deck, None, None, Type(gameType)))
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
