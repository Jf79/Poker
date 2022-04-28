package poker
package controller

import util.Observable
import model.Round
import model.Card

case class Controller(var round: Round) extends Observable {
    
    def this() = this(null)

    def setRound(round: Round) = 
        this.round = round

    def setPlayerCards() = 
        round = round.setPlayerCards()
        notifyObservers
    
    def holdCards(holdedCards: Vector[Int]) = 
        round = round.holdCards(holdedCards)
        notifyObservers
    
    override def toString = 
        round.toString

}
    

