package poker.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import model._


class GameSpec extends AnyWordSpec with Matchers { 
    "The Symbols" when {
        "there created" should {
            val symbols = List(Symbol.HEART, Symbol.DIAMOND, Symbol.CLUB, Symbol.SPADE)
            "be (HEART, DIAMOND, CLUB, SPADE)" in {
                symbols should be(Game.getSymbols())
            }
        }
    }
    "The Pictures" when {
        "there created" should {
            val pictures = List(Picture.TWO,Picture.THREE,Picture.FOUR,Picture.FIVE,Picture.SIX,Picture.SEVEN,
            Picture.EIGHT,Picture.NINE,Picture.TEN,Picture.JACK,Picture.QUEEN, Picture.KING,
            Picture.ACE)
            "be (TWO,THREE,FOUR,FIVE,SIX,SEVEN,EIGHT,NINE,TEN,JACK,QUEEN,KING,ACE)" in {
                pictures should be(Game.getPicutres())
            }
        }
    }
}
