package poker.model

import model._
import scala.util.Random

object CardsObject {

  def getSymbols(): List[Symbol] = {
    val symbols = List(Symbol.HEART, Symbol.DIAMOND, Symbol.CLUB, Symbol.SPADE)
    symbols
  }

  def getPicutres(): List[Picture] = {
    val pictures = List(
      Picture.TWO,
      Picture.THREE,
      Picture.FOUR,
      Picture.FIVE,
      Picture.SIX,
      Picture.SEVEN,
      Picture.EIGHT,
      Picture.NINE,
      Picture.TEN,
      Picture.JACK,
      Picture.QUEEN,
      Picture.KING,
      Picture.ACE
    )
    pictures
  }

  def createCards(): Array[Card] = {
    val symbols = getSymbols()
    val pictures = getPicutres()
    val cards = new Array[Card](52)
    var index = 0
    for (i <- 0 to symbols.length - 1) {
      for(j <- 0 to pictures.length - 1) {
        cards(index) = new Card(symbols(i), pictures(j), j + 2) 
        index += 1
      }
    }
    cards
  }

  def getRandomCard(cards: Array[Card], rand: Int): (Card, Array[Card]) = {
    val card = cards(rand)
    var index = 0
    val newdeck = cards.filter(!_.equals(card))
    (card, newdeck)
  }

  def getRandomCards(cards: Array[Card], n: Int): (Array[Card], Array[Card]) = {
    val randCards = new Array[Card](n)
    var deck = cards
    for (i <- 0 to n - 1) {
      val rand = Random.nextInt(deck.length - 1)
      val tuple = getRandomCard(deck, rand)
      randCards(i) = tuple._1
      deck = tuple._2
    }
    (randCards, deck)
  }

}
