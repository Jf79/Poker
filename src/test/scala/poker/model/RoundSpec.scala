package poker
package model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import CardsObject._
import Symbol._
import Picture._

class RoundSpec extends AnyWordSpec with Matchers {
  val player = new Player(1000)
  val bet = 100
  val deck = createCards()
  var round = new Round(player, deck, Some(bet), None, null)

  "A round when its created " should {
    "have a player with a 1000 $" in {
      round.player.money should be(1000)
    }
  }

  round = round.start()
  "A round when it set the player cards " should {
    "have a hand with 5 cards" in {
      round.hand.get.length should be(5)
    }
    "have a deck with 47 remaining cards" in {
      round.deck.length should be(47)
    }
  }
  val oldHand = round.hand.get
  val newRound = round.holdCards(Vector(2, 3, 4))
  "A round when you hold 3 cards, the player " should {
    "have 2 new cards" in {
      var newCards = 0
      oldHand.foreach(c => if (!newRound.hand.get.contains(c)) newCards += 1)
      newCards should be(2)
    }
  }

  val hand = Array(
    new Card(HEART, TWO, 2),
    new Card(HEART, THREE, 3),
    new Card(HEART, FOUR, 4),
    new Card(HEART, FIVE, 5),
    new Card(HEART, SIX, 6)
  )
  val cards = Array(
    new Card(SPADE, TWO, 2),
    new Card(SPADE, THREE, 3),
    new Card(SPADE, FOUR, 4),
    new Card(SPADE, FIVE, 5),
    new Card(SPADE, SIX, 6)
  )
  "A round when all cards are replaced, the new hand " should {
    var contain = false

    "have completly new cards" in {
      val newHand = round.replaceCards(Vector(), cards, hand)
      hand.foreach(c => if (newHand.contains(c)) { contain = true })
      contain should be(false)
    }
  }
  "A round when not a single card is replaced, the new hand " should {
    var contain = true
    "be the the same like the old hand" in {
      val newHand = round.replaceCards(Vector(1, 2, 3, 4, 5), cards, hand)
      hand.foreach(c => if (!newHand.contains(c)) contain = false)
      contain should be(true)
    }
  }
}
