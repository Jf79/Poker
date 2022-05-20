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
import model.{BetState, RiskTypeState, DealCardsState, HoldCardsState, EndState}

case class Controller(val player: Player) extends Observable:

  var round: Option[Round] = None

  // notifyObservers methods

  def doAndPublish(createround: (Array[Card]) => Round, deck: Array[Card]): Unit =
    round = Some(createround(deck))
    notifyObservers

  def doAndPublish(chooserisktype: String => Round, risk: String): Unit =
    round = Some(chooserisktype(risk))
    notifyObservers

  def doAndPublish(setbet: Int => Round, bet: Int): Unit =
    round = Some(setbet(bet))
    notifyObservers
  
  def doAndPublish(dealcards: => Round): Unit =
    round = Some(dealcards)
    notifyObservers

  def doAndPublish(holdcards: Vector[Int] => Round, holdedCards: Vector[Int]): Unit =
    round = Some(holdcards(holdedCards))
    notifyObservers
  
  // methods of round 
  
  def startRound(deck: Array[Card]): Round =   // Start State
    round = new Round(player, deck).state.execute().asInstanceOf[Option[Round]]
    round.get

  def chooseRiskType(risk: String): Round =   // Risk Type State
    round = round.get.state.execute(round.get.setRiskType, risk)
    round.get
  
  def setBet(bet: Int) : Round =  // Bet State
    round = round.get.state.execute(round.get.setBet, bet)
    round.get
  
  def dealCards(): Round =  // Deal Cards State
    round = round.get.state.execute(round.get.dealCards())
    round.get

  def holdCards(holdedCards: Vector[Int]): Round =  // Hold Cards State
    round = round.get.state.execute(round.get.holdCards, holdedCards)
    round.get

  def getStateOfRound() : State =
    round.get.state

  def createDeck(): Array[Card] =
    createCards()

  def hasEnoughCredit(): Boolean = 
    round.get.hasEnoughCredit()

  def getAllStates: Array[State] = 
    Array(RiskTypeState(round.get), BetState(round.get), DealCardsState(round.get), HoldCardsState(round.get), 
    EndState(round.get))
  
  // toString
  override def toString =
    round.get.toString
