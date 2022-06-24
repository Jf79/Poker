package poker
package controller
package controllerBaseImpl

import util.Observable
import util.Event
import util.GameEvent._

import model.round.RoundInterface
import model.round.CreateRound
import model.player.PlayerInterface

import model.card.CardInterface

import util.CardsObject._
import util.State
import util._
import model.round.roundBaseImpl.{BetState, RiskTypeState, DealCardsState, HoldCardsState, EvaluationState}
import com.google.inject.Inject
import poker.model.fileIO.fileIOxml.FileIO
import poker.model.fileIO.FileIOInterface

case class Controller @Inject() (player: PlayerInterface, fileIo: FileIOInterface) extends ControllerInterface:

  val undoManager = new UndoManager[RoundInterface]
  var round: Option[RoundInterface] = None

  // notifyObservers methods

  def doAndPublish(createround: (Array[CardInterface]) => RoundInterface, deck: Array[CardInterface]): Unit =
    round = Some(createround(deck))
    notifyObservers(PLAY)

  def doAndPublish(chooserisktype: String => RoundInterface, risk: String): Unit =
    round = Some(chooserisktype(risk))
    notifyObservers(PLAY)

  def doAndPublish(setbet: Int => RoundInterface, bet: Int): Unit =
    round = Some(setbet(bet))
    notifyObservers(PLAY)
  
  def doAndPublish(doThis: => RoundInterface): Unit = // dealcards and evaluation
    round = Some(doThis)
    notifyObservers(PLAY)

  def doAndPublish(holdcards: Vector[Int] => RoundInterface, holdedCards: Vector[Int]): Unit =
    round = Some(holdcards(holdedCards))
    notifyObservers(PLAY)
  
  // methods of round 
  
  def startIntro(): Unit = notifyObservers(INTRO)

  def startTheGame(): Unit = notifyObservers(START)

  def endTheGame(): Unit = notifyObservers(EXIT)

  def startRound(deck: Array[CardInterface]): RoundInterface =   // Start State
    round = CreateRound(player, deck).state.execute().asInstanceOf[Option[RoundInterface]]
    round.get

  def chooseRiskType(risk: String): RoundInterface =   // Risk Type State
    round = round.get.state.execute(round.get.setRiskType, risk)
    round.get
  
  def setBet(bet: Int) : RoundInterface =  // Bet State
    //round = round.get.state.execute(round.get.setBet, bet)
    round = Some(undoManager.doStep(round.get, BetCommand(bet)))
    round.get
  
  def dealCards(): RoundInterface =  // Deal Cards State
    round = round.get.state.execute(round.get.dealCards())
    round.get

  def holdCards(holdedCards: Vector[Int]): RoundInterface =  // Hold Cards State
    round = round.get.state.execute(round.get.holdCards, holdedCards)
    round.get
  
  def evaluation(): RoundInterface = 
    round = round.get.state.execute(round.get.evaluation())
    fileIo.save(round.get, "round")
    fileIo.save(fileIo.load, "copy")
    round.get
  
  def getStateOfRound(): State =
    round.get.state

  def createDeck(): Array[CardInterface] =
    createCards()

  def hasEnoughCredit(): Boolean = 
    player.getMoney() > 0
  
  def clearUndoManager() =  undoManager.clear()
  
  def undo() = round = Some(undoManager.undoStep(round.get))

  def getHandOfPlayer(): Array[CardInterface] = round.get.getHandOfPlayer()

  def getCreditOfPlayer(): Int = player.getMoney()
  
  // toString
  override def toString =
    round.get.toString
