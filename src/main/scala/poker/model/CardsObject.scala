package poker
package model

import scala.util.Random.nextInt

object CardsObject:

  def createCards(): Array[Card] =
    val cards = new Array[Card](52)
    for (i <- 0 to 51)
      cards(i) =
        new Card(Symbol.values(i % 4), Picture.values(i % 13), (i % 13) + 2)
    cards

  def getRandomCard(cards: Array[Card], rand: Int): (Card, Array[Card]) =
    val card = cards(rand)
    val newdeck = cards.filter(!_.equals(cards(rand)))
    (card, newdeck)

  def getRandomCards(cards: Array[Card], n: Int): (Array[Card], Array[Card]) =
    val randCards = new Array[Card](n)
    var deck = cards.clone
    for (i <- 1 to n)
      val tuple = getRandomCard(deck, nextInt(deck.length - 1))
      randCards(i - 1) = tuple._1
      deck = tuple._2
    (randCards, deck)
