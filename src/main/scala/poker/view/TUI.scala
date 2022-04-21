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
      gameLoop(money, player)
      println("Your remaining money: " + player.money)
    }

    def gameLoop(money: Int, player:Player) : Player = {
      var continue = true
      var theMoney = money
      var thePlayer = player
      while(continue) {
        val deck = createDeck()
        val randCardsAndDeck = getRandomCards(deck, 5)
        thePlayer = new Player(randCardsAndDeck._1, thePlayer.money)
        println(printGame(player))
        thePlayer = startRound(player, randCardsAndDeck._2)
        println(printGame(player))
        continue = checkInput(readLine("Do you wanna continue(c) or quit(q) ?"))
      }
      thePlayer
    }    

    def startRound(player: Player, deck: Array[Card]): Player =
      val newCards = checkInput(readLine("Which cards you wanna hold ?").split(" "), deck)
      val money = player.money + 0
      new Player(newCards, money)

    def checkInput(input: Array[String], deck: Array[Card]): Array[Card] = 
      val cards = new Array[Int](input.length)
      var i = 0
      for(c <- input)
        cards(i) = c.toInt
      holdCards(deck, cards)
    
    def holdCards(deck:Array[Card], holdedCards:Array[Int]): Array[Card] = 
      val hand = new Array[Card](5)
      hand

    def checkInput(input: String): Boolean =
      input.equals("c")

    def printGame(player: Player): String = 
      var game = "\n"
      game += printNumbers() + printCards(player.cards)
      game

    def printCards(hand: Array[Card]): String =
      var cards = "\n"
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
