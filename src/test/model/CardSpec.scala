import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import poker.model

class CardSpec extends WordSpec with Matchers { 
  "A Card" when {
    "when its created" should {
      val card = Card(Symbol.HEART, Picture.ACE)
      "have symbol HEART" in {
        card.symbol should be(Symbol.HEART)
      }
      "have pictrue ACE" in {
        card.pictrue should be(Picture.ACE)
      }
    }
    "its printed" should {
       "show" in{
          card.toString should be("A (HEART)")
       }
    }
  }
}

