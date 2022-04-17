package model
import scala.util.Random

object Game {

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
    for (i <- 0 to pictures.length - 1) {
      for (j <- 0 to symbols.length - 1) {
        cards(i) = new Card(symbols(j), pictures(i), i)
      }
    }
    cards
  }

  def getRandomCard(cards: Array[Card]): (Card, Array[Card]) = {
    val indexCard = (math.random * (cards.length - 1)).toInt
    val card = cards(indexCard)
    val deck = new Array[Card](cards.length - 1)
    var index = 0
    for (i <- 0 to cards.length - 1) {
      if (i != indexCard) {
        deck(index) = cards(i)
        index += 1
      }
    }
    (card, deck)
  }

  def getRandomCards(cards: Array[Card], n: Int): (Array[Card], Array[Card]) = {
    // Todo: get n random cards of the array cards, using the function getRandomCard(cards)
    val hand = new Array[Card](5)
    var deck = cards
    for (i <- 0 to n - 1) {
      val tuple = getRandomCard(deck)
      hand(i) = tuple._1
      deck = tuple._2
    }
    (hand, deck)
  }

}
