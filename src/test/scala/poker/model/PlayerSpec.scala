package poker.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import model._


class PlayerSpec extends AnyWordSpec with Matchers { 
  "A Player" when {
    "when its created" should {
        val cards = new Array[Card](5)
        cards(0) = new Card(Symbol.HEART, Picture.TEN, 10);cards(1) = new Card(Symbol.HEART, Picture.JACK, 11);
        cards(2) = new Card(Symbol.HEART, Picture.QUEEN, 12);cards(3) = new Card(Symbol.HEART, Picture.KING, 13);
        cards(4) = new Card(Symbol.HEART, Picture.ACE, 14);   
        val player = new Player(cards, 100)    
        "have 5 cards" in {
            player.hand should be(cards)
        }
        "have 100 $" in {
            player.money should be(100)
        }
    }
  }
}

