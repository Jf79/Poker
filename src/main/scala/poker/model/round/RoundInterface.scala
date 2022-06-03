package poker
package model
package round

import util.RiskType
import util.State
import util.Stateable

import model.player.PlayerInterface
import model.card.CardInterface

import scala.util.Try


trait RoundInterface extends Stateable:
    var updateMessage: String
    def copyRound() : RoundInterface
    def setRiskType(risk: String): Try[RoundInterface]
    def setBet(b: Int): Try[RoundInterface] 
    def dealCards(): RoundInterface
    def holdCards(holdedCards: Vector[Int]): Try[RoundInterface]
    def evaluation(): RoundInterface
    def hasEnoughCredit() : Boolean 


object CreateRound :
    def apply (player: PlayerInterface, deck: Array[CardInterface]) = new Round(player, deck)
        

  
