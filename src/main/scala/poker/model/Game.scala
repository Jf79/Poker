package model
import scala.util.Random

object Game {

    def getSymbols() : List[Symbol] = {
        val symbols = List(Symbol.HEART, Symbol.DIAMOND, Symbol.CLUB, Symbol.SPADE)
        symbols
    }

    def getPicutres() : List[Picture] = {
        val pictures = List(Picture.TWO, Picture.THREE, Picture.FOUR, Picture.FIVE, Picture.SIX, Picture.SEVEN, 
        Picture.EIGHT, Picture.NINE, Picture.TEN, Picture.JACK, Picture.QUEEN, Picture.KING, Picture.ACE)
        pictures
    } 


    def createCards() : Array[Card] = {
       // Todo: create all 52 cards 
       null 
    }

    def getRandomCard(cards: Array[Card]) : Card = {
        // Todo: get random card of the array cards
        null
    }

    def getRandomCards(cards: Array[Card], n: Int) : Array[Card] = {
        // Todo: get n random cards of the array cards, using the function getRandomCard
        null
    }

}

