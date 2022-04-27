package view

import scala.io.StdIn._
import model._
import poker.model._
import poker.model.CardsObject._

class TUI() {
  
    def run = {
      println("Good Luck !")
      val money = 500
      var player = new Player(new Array[Card](5), money)
      gameLoop(player)
      println("Your remaining money: " + player.money)
    }

    def gameLoop(player: Player) : Player = {
      var continue = true
      var thePlayer = player
      while(continue) {
        val deck = createDeck() 
        val randCardsAndDeck = getRandomCards(deck, 5)
        thePlayer = new Player(randCardsAndDeck._1, thePlayer.money)
        thePlayer = startFirstRound(thePlayer, randCardsAndDeck._2)
        println(printGame(thePlayer))
        continue = checkInput(readLine("Do you wanna continue(c) or quit(q) ?\n"))
      }
      thePlayer
    }    

    
    def startFirstRound(player: Player, deck: Array[Card]): Player =
      println(printGame(player))
      val newCards = processInput(readLine("Which cards you wanna hold ?\n").split(" "), deck, player)
      player.setHand(newCards)
    

    def processInput(input: Array[String], deck: Array[Card], player: Player): Array[Card] = 
      val cards = new Array[Int](input.length)
      for(i <- 0  to input.length - 1)
        cards(i) = input(i).toInt - 1
      holdCards(deck, cards, player)
    
    
    def holdCards(deck: Array[Card], holdedCards: Array[Int], player: Player): Array[Card] = 
      val newHand = player.hand.clone
      val randCardsAndDeck = getRandomCards(deck, holdedCards.length)
      var index = 0
      for(i <- 0 to 4 if(!holdedCards.contains(i))) 
          newHand(i) = randCardsAndDeck._1(index)
          index += 1
      newHand
    

    def checkInput(input: String): Boolean =
      input.equals("c")

    
    def printGame(player: Player): String = 
      var game = "\n\n" + printNumbers() + "\n\n" + printCards(player.hand) + "\n\n"
      game

    
    def printCards(hand: Array[Card]): String =
      var cards = ""
      hand.foreach(h => cards += h.toString + "\t")
      cards

    
    def printNumbers(): String =
      var numbers = ""
      for(i <- 1 to 5)
        numbers += "["+ i +"]\t\t"
      numbers

    
    def createDeck(): Array[Card] =
      createCards()
    

}
