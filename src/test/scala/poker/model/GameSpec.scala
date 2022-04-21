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
    "The Cards" when {
        "the created" should {
            val cards = Game.createCards()
            val card = new Card(Symbol.HEART, Picture.TWO, 2)
            "have 13 heart cards" in {
                var hearts = 0
                cards.foreach(c => if(c.symbol.equals(Symbol.HEART)){hearts+=1})
                hearts should be(13)
            }
            "have 13 diamond cards" in {
                var diamonds = 0
                cards.foreach(c => if(c.symbol.equals(Symbol.DIAMOND)){diamonds+=1})
                diamonds should be(13)
            }
            "have 13 spade cards" in {
                var spades = 0
                cards.foreach(c => if(c.symbol.equals(Symbol.SPADE)){spades+=1})
                spades should be(13)
            }
            "have 13 club cards" in {
                var clubs = 0
                cards.foreach(c => if(c.symbol.equals(Symbol.CLUB)){clubs+=1})
                clubs should be(13)
            }
        }
    }
}
