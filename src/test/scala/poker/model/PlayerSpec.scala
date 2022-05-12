package poker.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import model._


class PlayerSpec extends AnyWordSpec with Matchers { 
  "A Player" when {
    "it get a new hand" should {
      val player = new Player(Array(new Card(Symbol.HEART, Picture.TEN, 10)), 1000)
      val newPlayer = player.setHand(Array(new Card(Symbol.HEART, Picture.ACE, 14)))
      "return a player with different hand" in {
        newPlayer.hand.equals(player.hand) should be(false)
      }
      "return a player with the same amount on money" in {
        newPlayer.money should be(player.money)
      }
    }
    "when its created" should {
        val cards = new Array[Card](5)
        cards(0) = new Card(Symbol.HEART, Picture.TEN, 10);cards(1) = new Card(Symbol.HEART, Picture.JACK, 11);
        cards(2) = new Card(Symbol.HEART, Picture.QUEEN, 12);cards(3) = new Card(Symbol.HEART, Picture.KING, 13);
        cards(4) = new Card(Symbol.HEART, Picture.ACE, 14);   
        val player = new Player(cards, 100)    
        "have it cards" in {
            player.hand should be(cards)
        }
        "have 100 $" in {
            player.money should be(100)
        }
    }
  }
}

