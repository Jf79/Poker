package poker
package model

import CardsObject._


case class Round(var player: Player, var deck: Array[Card], bet: Int) :

    def setPlayerCards() : Round = 
      val tuple = getRandomCards(deck, 5)
      player = player.setHand(tuple._1)
      deck = tuple._2
      this


    def holdCards(holdedCards: Vector[Int]) : Round =
      val cards = getRandomCards(deck, 5 - holdedCards.length)._1
      player = player.setHand(replaceCards(holdedCards, cards, player.hand))
      this


    def replaceCards(holdedCards: Vector[Int], cards: Array[Card], hand: Array[Card]) : Array[Card] =
      var i = 0
      val newHand = hand.clone
      for(c <- 1 to 5 if(!holdedCards.contains(c)))
        newHand(c - 1) = cards(i); i += 1
      newHand
    

    override def toString = 
      "\n\n" + printNumbers + "\n\n" + printCards + "\n\n"
    

    def printNumbers : String =
      (1 to 5).map("["+ _.toString + "]\t\t").mkString
    

    def printCards : String =
      player.hand.map(_.toString + "\t").mkString
