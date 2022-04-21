package view

import java.lang.StringBuilder
import scala.io.StdIn._
import model._
import model.Game._

class TUI() {
  
    def run = {
      println("Good Luck !")
      val money = 500
      var player = new Player(new Array[Card](5), money)
      gameLoop(player)
      println("Your remaining money: " + player.money)
    }

    def gameLoop(player:Player) : Player = {
      var continue = true
      var thePlayer = player
      while(continue) {
        val deck = createDeck() 
        val randCardsAndDeck = getRandomCards(deck, 5)
        thePlayer = new Player(randCardsAndDeck._1, thePlayer.money)
        println(printGame(thePlayer))
        thePlayer = startRound(thePlayer, randCardsAndDeck._2)
        println(printGame(thePlayer))
        continue = checkInput(readLine("Do you wanna continue(c) or quit(q) ?\n"))
      }
      thePlayer
    }    

    def startRound(player: Player, deck: Array[Card]): Player =
      val newCards = checkInput(readLine("Which cards you wanna hold ?\n").split(" "), deck, player)
      val money = player.money + 0
      new Player(newCards, money)

    def checkInput(input: Array[String], deck: Array[Card], player: Player): Array[Card] = 
      val cards = new Array[Int](input.length)
      var i = 0
      for(c <- input)
        cards(i) = c.toInt
        i += 1
      holdCards(deck, cards, player)
    
    def holdCards(deck:Array[Card], holdedCards:Array[Int], player: Player): Array[Card] = 
      val newHand = Game.getRandomCards(deck, 5)._1
      val oldHand = player.cards
      for(h <- holdedCards)
        newHand(h - 1) = oldHand(h - 1)
      newHand

    def checkInput(input: String): Boolean =
      input.equals("c")

    def printGame(player: Player): String = 
      var game = "\n"
      game += printNumbers() + printCards(player.cards) + "\n\n"
      game

    def printCards(hand: Array[Card]): String =
      var cards = "\n\n"
      hand.foreach(h => cards += h.toString + "\t")
      cards

    def printNumbers(): String =
      var numbers = "\n"
      for(i <- 1 to 5)
        numbers += "["+ i +"]\t\t"
      numbers

    def createDeck(): Array[Card] =
      Game.createCards()
    

}
