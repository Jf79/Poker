package model
import scala.util.Random

object Game {

    private[Game] def getSymbols() : List[Symbol] = {
        val symbols = List(Symbol.HEART, Symbol.DIAMOND, Symbol.CLUB, Symbol.SPADE)
        symbols
    }

    private[Game] def getPicutres() : List[Picture] = {
        val pictures = List(Picture.TWO, Picture.THREE, Picture.FOUR, Picture.FIVE, Picture.SIX, Picture.SEVEN, 
        Picture.EIGHT, Picture.NINE, Picture.TEN, Picture.JACK, Picture.QUEEN, Picture.KING, Picture.ACE)
        pictures
    } 

   /* def createCards() : Array[Card] = {
        
    }*/

    /*+:* def getRandomCard(cards: Array[Card]) : Card = {
        val random = new Random(cards.length)
    }*/

   /* def getRandomCards(cards: Array[Card], number: Int) : Array[Card] = {
        val playerCards = new Array[Card](number)
        
    }*/

}

