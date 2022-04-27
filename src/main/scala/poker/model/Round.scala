package poker
package model

import CardsObject._


case class Round(var player: Player, var deck: Array[Card], bet: Int) {

    def setPlayerCards() : Round = 
        val handAndDeck = getRandomCards(deck, 5)
        player = player.setHand(handAndDeck._1)
        deck = handAndDeck._2
        this

    def holdCards(holdedCards: Vector[Int]): Round =
        val cards = getRandomCards(deck, 5 - holdedCards.length)._1
        val hand = player.hand.clone
        var index = 0
        for(i <- 0 to 4 if(!holdedCards.contains(i + 1)))
            hand(i) = cards(index)
            index += 1
        player = player.setHand(hand)
        this

    override def toString = 
      "\n" + printNumbers() + "\n\n" + printCards() + "\n"
    

    def printNumbers(): String =
      (1 to 5).map("["+ _.toString + "]\t\t").mkString
    

    def printCards(): String =
      player.hand.map(_.toString + "\t").mkString
     


    
}

