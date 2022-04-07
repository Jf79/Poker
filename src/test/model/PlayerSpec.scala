import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import poker.model

class PlayerSpec extends WordSpec with Matchers { 
  "A Player" when {
    "when its created" should {
        val cards = new Array[Card](1)
        cards(0) = new Card(Symbol.HEART, Picture.TEN);cards(1) = new Card(Symbol.HEART, Picture.JACK);
        cards(2) = new Card(Symbol.HEART, Picture.QUEEN);cards(3) = new Card(Symbol.HEART, Picture.KING);
        cards(4) = new Card(Symbol.HEART, Picture.ACE);   
        val player = new Player(cards, 100)    
        "have 5 cards" in {
            player.cards should be(cards)
        }
        "have 100 $" in {
            player.money should be(100)
        }
    }
  }
}

