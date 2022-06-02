package poker
package controller

import util.Observable
import util.Event
import util.GameEvent._

import model.HighRisk
import model.Round
import model.Card
import model.CardsObject._
import model.Player
import util.State
import model.RiskType
import util._
import model.{BetState, RiskTypeState, DealCardsState, HoldCardsState, EvaluationState}

case class Controller(player: Player) extends Observable:

  val undoManager = new UndoManager[Round]
  var round: Option[Round] = None

  // notifyObservers methods

  def doAndPublish(createround: (Array[Card]) => Round, deck: Array[Card]): Unit =
    round = Some(createround(deck))
    notifyObservers(PLAY)

  def doAndPublish(chooserisktype: String => Round, risk: String): Unit =
    round = Some(chooserisktype(risk))
    notifyObservers(PLAY)

  def doAndPublish(setbet: Int => Round, bet: Int): Unit =
    round = Some(setbet(bet))
    notifyObservers(PLAY)
  
  def doAndPublish(doThis: => Round): Unit = // dealcards and evaluation
    round = Some(doThis)
    notifyObservers(PLAY)

  def doAndPublish(holdcards: Vector[Int] => Round, holdedCards: Vector[Int]): Unit =
    round = Some(holdcards(holdedCards))
    notifyObservers(PLAY)
  
  // methods of round 
  
  def startRound(deck: Array[Card]): Round =   // Start State
    round = new Round(player, deck).state.execute().asInstanceOf[Option[Round]]
    round.get

  def chooseRiskType(risk: String): Round =   // Risk Type State
    round = round.get.state.execute(round.get.setRiskType, risk)
    round.get
  
  def setBet(bet: Int) : Round =  // Bet State
    //round = round.get.state.execute(round.get.setBet, bet)
    round = Some(undoManager.doStep(round.get, BetCommand(bet)))
    round.get
  
  def dealCards(): Round =  // Deal Cards State
    round = round.get.state.execute(round.get.dealCards())
    round.get

  def holdCards(holdedCards: Vector[Int]): Round =  // Hold Cards State
    round = round.get.state.execute(round.get.holdCards, holdedCards)
    round.get
  
  def evaluation(): Round = 
    round = round.get.state.execute(round.get.evaluation())
    round.get

  def getStateOfRound(): State =
    round.get.state

  def createDeck(): Array[Card] =
    createCards()

  def hasEnoughCredit(): Boolean = 
    round.get.hasEnoughCredit()
  
  def clearUndoManager() = 
    undoManager.clear()
  
  def undo() = round = Some(undoManager.undoStep(round.get))
  
  // toString
  override def toString =
    round.get.toString
