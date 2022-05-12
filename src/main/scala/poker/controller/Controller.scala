package poker
package controller

import util.Observable
import model.Round
import model.Card
import model.CardsObject._
import model.Player

case class Controller(val player: Player) extends Observable :
    
    var round: Round = null;

    def doAndPublish(doThis: => Round) = 
        round = doThis
        notifyObservers

    
    def doAndPublish(doThis: Vector[Int] => Round, holdedCards: Vector[Int]) = 
        round = doThis(holdedCards)
        notifyObservers


    def setRound(bet: Int) = 
        round = new Round(player, createDeck(), bet)


    def setPlayerCards() : Round = 
        round.setPlayerCards()

    
    def holdCards(holdedCards: Vector[Int]) = 
        round = round.holdCards(holdedCards)
        notifyObservers

    
    def createDeck(): Array[Card] =
        createCards()
        
    
    override def toString = 
        round.toString

    

