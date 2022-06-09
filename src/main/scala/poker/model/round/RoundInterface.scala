package poker
package model
package round

import util.RiskType
import util.State
import util.Stateable
import util.Combination

import model.player.PlayerInterface
import model.card.CardInterface

import scala.util.Try


trait RoundInterface extends Stateable:
    var updateMessage: String
    var riskType: Option[RiskType] = None 
    var combination: Option[Combination] = None
    def getOutcome(): Int
    def getCombinationHand(): Option[Array[CardInterface]]
    def getHandOfPlayer(): Array[CardInterface]
    def copyRound() : RoundInterface
    def setRiskType(risk: String): Try[RoundInterface]
    def setBet(b: Int): Try[RoundInterface] 
    def dealCards(): RoundInterface
    def holdCards(holdedCards: Vector[Int]): Try[RoundInterface]
    def evaluation(): RoundInterface
    def hasEnoughCredit() : Boolean 


object CreateRound :
    def apply (player: PlayerInterface, deck: Array[CardInterface]): RoundInterface = new Round(player, deck)
        

  
