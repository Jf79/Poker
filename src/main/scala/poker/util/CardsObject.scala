package poker
package util


import util.Symbol
import util.Picture
import model.card.CardInterface
import model.card.CreateCard
import scala.util.Random.nextInt

object CardsObject:

  def createCards(): Array[CardInterface] =
    val cards = new Array[CardInterface](52)
    for (i <- 0 to 51)
      cards(i) = CreateCard(Symbol.values(i % 4), Picture.values(i % 13), (i % 13) + 2)
    cards

  def getRandomCard(cards: Array[CardInterface], rand: Int): (CardInterface, Array[CardInterface]) =
    val card = cards(rand)
    val newdeck = cards.filter(!_.equals(cards(rand)))
    (card, newdeck)

  def getRandomCards(cards: Array[CardInterface], n: Int): (Array[CardInterface], Array[CardInterface]) =
    val randCards = new Array[CardInterface](n)
    var deck = cards.clone
    for (i <- 1 to n)
      val tuple = getRandomCard(deck, nextInt(deck.length - 1))
      randCards(i - 1) = tuple._1
      deck = tuple._2
    (randCards, deck)
