package poker
package controller

import util.Observable
import model.Round
import model.Card
import model.CardsObject._
import model.Player

case class Controller(val player: Player, var round: Option[Round])
    extends Observable:

  def doAndPublish(doThis: => Round): Unit =
    round = Some(doThis)
    notifyObservers

  def doAndPublish(
      doThis: Vector[Int] => Round,
      holdedCards: Vector[Int]
  ): Unit =
    round = Some(doThis(holdedCards))
    notifyObservers

  def doAndPublish(doThis: Int => Round, bet: Int): Unit =
    round = Some(doThis(bet))
    notifyObservers

  def createRound(bet: Int): Round =
    round = Some(new Round(player, createDeck(), bet, None))
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
