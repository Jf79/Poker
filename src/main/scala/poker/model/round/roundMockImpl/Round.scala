package poker
package model
package round
package roundMockImpl

import util.CardsObject._
import util.CombinationObject._
import util._
import model.player.CreatePlayer
import player.PlayerInterface
import card.CardInterface

import scala.util.{Try, Success, Failure}


case class Round(player: PlayerInterface, var deck: Array[CardInterface]) extends RoundInterface:

  var bet: Option[Int] = null
  var hand: Option[Array[CardInterface]] = null
  var updateMessage: String = null
  var combinationHand: Option[Array[CardInterface]] = null
  var outcome: Int = 0

  // stateable
  var state = null

  override def copyRound() : RoundInterface = null

  def returnCopy[T](arg: Option[T]): Option[T] = null

  def handle(event: Event): State = null
  
  // riskType state
  
  override def setRiskType(risk: String): Try[RoundInterface] = null

  def handleTryRiskType(rType : Try[RiskType]): Try[Round] = null

  // bet state 

  override def setBet(b: Int): Try[RoundInterface] = null
  
  def handleTryBet(bType : Try[Int]) : Try[Round] = null

  // cards state

  override def dealCards(): RoundInterface = null
  
  // replace state 

  override def holdCards(holdedCards: Vector[Int]): Try[RoundInterface] = null

  def replaceCards(holdedCards: Vector[Int], cards: Array[CardInterface], hand: Array[CardInterface]): Array[CardInterface] =
    null

  // evaluation state

  override def evaluation(): Round = null
    

  def checkCombination(hand: Array[CardInterface]): (Option[Combination], Option[Array[CardInterface]]) = 
    null
        
  def filterCombination(tuple: (Option[Combination], Option[Array[CardInterface]]), hand: Option[Array[CardInterface]]) : Option[Array[CardInterface]]=
    null
  
  override def hasEnoughCredit(): Boolean =  false
  
  override def getHandOfPlayer(): Array[CardInterface] = null 

  override def getCombinationHand(): Option[Array[CardInterface]] = null

  override def getOutcome(): Int = 0

  override def getBet(): Int = 0

  override def toString = null

