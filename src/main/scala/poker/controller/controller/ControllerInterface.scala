package poker
package controller
package controller

import util.Observable
import util.State

import model.round.RoundInterface
import model.round.CreateRound
import model.player.PlayerInterface
import model.card.CardInterface
import poker.model.round.Round

trait ControllerInterface extends Observable:
    
    var round: Option[RoundInterface]

    def doAndPublish(createround: (Array[CardInterface]) => RoundInterface, deck: Array[CardInterface]): Unit
    def doAndPublish(chooserisktype: String => RoundInterface, risk: String): Unit
    def doAndPublish(setbet: Int => RoundInterface, bet: Int): Unit 
    def doAndPublish(doThis: => RoundInterface): Unit 
    def doAndPublish(holdcards: Vector[Int] => RoundInterface, holdedCards: Vector[Int]): Unit

    def startIntro(): Unit
    def startTheGame(): Unit
    def endTheGame(): Unit
    def startRound(deck: Array[CardInterface]): RoundInterface
    def chooseRiskType(risk: String): RoundInterface
    def setBet(bet: Int) : RoundInterface
    def dealCards(): RoundInterface
    def holdCards(holdedCards: Vector[Int]): RoundInterface
    def evaluation(): RoundInterface 
    def getStateOfRound(): State 
    def createDeck(): Array[CardInterface]
    def hasEnoughCredit(): Boolean
    def getHandOfPlayer(): Array[CardInterface]
    def getCreditOfPlayer(): Int
    def clearUndoManager(): Unit
    def undo(): Unit




