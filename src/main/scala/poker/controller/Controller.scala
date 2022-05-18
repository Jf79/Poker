package poker
package controller

import util.Observable
import util.Event
import model.HighRisk
import model.Round
import model.Card
import model.CardsObject._
import model.Player
import util.State
import model.RiskType
import util._

case class Controller(val player: Player) extends Observable:

  var round: Option[Round] = None

  // notifyObservers methods

  def doAndPublish(createround: (Array[Card]) => Round, deck: Array[Card]): Unit =
    round = Some(createround(deck))
    notifyObservers

  def doAndPublish(setbet: Int => Round, bet: Int): Unit =
    round = Some(setbet(bet))
    notifyObservers
  
  def doAndPublish(setcards: => Round): Unit =
    round = Some(setcards)
    notifyObservers

  def doAndPublish(holdcards: Vector[Int] => Round, holdedCards: Vector[Int]): Unit =
    round = Some(holdcards(holdedCards))
    notifyObservers
  
  // methods of round 
  
  def createRound(deck: Array[Card]): Round =   // Start State
    round = Some(new Round(player, deck))
    round.get

  def chooseRiskType(risk: String): Round =   // Risk Type State
    round = Some(round.get.setRiskType(risk))
    round.get
  
  def setBet(bet: Int) : Round =  // Bet State
    round.get
  
  def dealCards(): Round =  // Deal Cards State
    round = Some(round.get.dealCards())
    round.get

  def holdCards(holdedCards: Vector[Int]): Round =  // Hold Cards State
    round = Some(round.get.holdCards(holdedCards))
    round.get

  def createDeck(): Array[Card] =
    createCards()

  // toString
  override def toString =
    round.get.toString
