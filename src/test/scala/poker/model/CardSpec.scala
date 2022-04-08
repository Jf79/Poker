package poker.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import model._

class CardSpec extends AnyWordSpec with Matchers { 
  "A Card" when {
    "when its created" should {
      val card = Card(Symbol.HEART, Picture.ACE, 14)
      "have symbol HEART" in {
        card.symbol should be(Symbol.HEART)
      }
      "have pictrue ACE" in {
        card.picture should be(Picture.ACE)
      }
    }
    "its printed" should {
      val card = Card(Symbol.HEART, Picture.ACE, 14)
      "show" in{
        card.toString should be("A (HEART)")
      }
    }
  }
}

