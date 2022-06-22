package poker
package controller
package controllerMockImpl

import util.Observable
import util.Event
import util.GameEvent._

import model.round.RoundInterface
import model.round.CreateRound
import model.player.PlayerInterface

import model.card.CardInterface
import controller.ControllerInterface



import util.CardsObject._
import util.State
import util._
import model.round.{BetState, RiskTypeState, DealCardsState, HoldCardsState, EvaluationState}
import controller.ControllerInterface

case class Controller(player: PlayerInterface) extends ControllerInterface:

  val undoManager = null
  var round: Option[RoundInterface] = null

  // notifyObservers methods

  def doAndPublish(createround: (Array[CardInterface]) => RoundInterface, deck: Array[CardInterface]): Unit =
    return

  def doAndPublish(chooserisktype: String => RoundInterface, risk: String): Unit =
    return

  def doAndPublish(setbet: Int => RoundInterface, bet: Int): Unit =
    return
  def doAndPublish(doThis: => RoundInterface): Unit = // dealcards and evaluation
    return
  def doAndPublish(holdcards: Vector[Int] => RoundInterface, holdedCards: Vector[Int]): Unit =
    return
  
  // methods of round 
  
  def startIntro(): Unit = return 

  def startTheGame(): Unit = return

  def endTheGame(): Unit = return

  def startRound(deck: Array[CardInterface]): RoundInterface =   // Start State
    null

  def chooseRiskType(risk: String): RoundInterface =   // Risk Type State
    null
  
  def setBet(bet: Int) : RoundInterface =  // Bet State
    null
  
  def dealCards(): RoundInterface =  // Deal Cards State
    null

  def holdCards(holdedCards: Vector[Int]): RoundInterface = null // Hold Cards State
  
  def evaluation(): RoundInterface = null
  
  def getStateOfRound(): State = null

  def createDeck(): Array[CardInterface] = null

  def hasEnoughCredit(): Boolean = false
  
  def clearUndoManager() = return 
  
  def undo() = return 

  def getHandOfPlayer(): Array[CardInterface] = null

  def getCreditOfPlayer(): Int = 0
  
  // toString
  override def toString = null
