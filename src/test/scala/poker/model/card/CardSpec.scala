package poker
package model

import util.Symbol
import util.Picture

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import poker.model.card.cardBaseImpl.Card

//import model._

class CardSpec extends AnyWordSpec with Matchers { 
  "A Card" when {
    val card = Card(Symbol.HEART, Picture.ACE, 14)
    "when its created" should {
      "have symbol HEART" in {
        card.symbol should be(Symbol.HEART)
      }
      "have pictrue ACE" in {
        card.picture should be(Picture.ACE)
      }
    }
    "when its printed" should {
      "is printed" in {
        card.toString should be("A (HEART)")
      }
    }
  }
}