package view

import java.lang.StringBuilder
import model._

class TUI() {
  
    def run = {
      val field = new StringBuilder()
      var deck = createDeck
    }

    def createDeck: Array[Card] =
      val deck = Game.createCards()
      deck

    def printGame(player: Player): StringBuilder = 
      val game = new StringBuilder()
      game.append(printNumbers())
      game.append("\n")
      game.append(printCards(player.cards))
      game

    def printCards(hand: Array[Card]): StringBuilder =
      val cards = new StringBuilder()
      hand.foreach(c => cards.append(c.toString + " "))
      cards

    def printNumbers(): StringBuilder =
      val numbers = new StringBuilder()
      for(i <- 1 to 5)
        numbers.append(i +"\t")
      numbers

    
    

}
