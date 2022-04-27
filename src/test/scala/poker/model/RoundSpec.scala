package poker
package model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import CardsObject._


class RoundSpec extends AnyWordSpec with Matchers {
    "A Round " when {
        val player = new Player(100)
        val round = new Round(player, 10)
        "it's created " should {
            "have a player " in {
                round.player should be(player)
            }
            "have a bet " in {
                round.bet should be(10)
            }
        }
        "the player cards are set, the player" should {
            val cards = createCards()
            val round = new Round(player, 10)
            val deck = round.setPlayerCards(cards)
            "have a hand of five cards" in {
                round.player.hand.length should be(5)
            }
            "the remaining cards should be 47" in {
                deck.length should be(47)
            }
        }  
        "the player hold a card, the new hand " should {
            var deck = createCards()
            val tuple = getRandomCards(deck, 5)
            val hand = tuple._1
            deck = tuple._2
            val newHand = round.holdCards(deck, hand, Array(3))
            "still have this card" in {
                hand(2) should be(newHand(2))
            }
        }
    }
}