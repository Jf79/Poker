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
    "The card deck" when {
        "its created" should {
            val cards = Game.createCards()
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
            "have 4 TWOS" in {
                var i = 0
                cards.foreach(c => if(c.picture.equals(Picture.TWO)){i+=1})
                i should be(4)
            }
            "have 4 THREES" in {
                var i = 0
                cards.foreach(c => if(c.picture.equals(Picture.THREE)){i+=1})
                i should be(4)
            }
            "have 4 FOURS" in {
                var i = 0
                cards.foreach(c => if(c.picture.equals(Picture.FOUR)){i+=1})
                i should be(4)
            }
            "have 4 FIVES" in {
                var i = 0
                cards.foreach(c => if(c.picture.equals(Picture.FIVE)){i+=1})
                i should be(4)
            }
            "have 4 SIXS" in {
                var i = 0
                cards.foreach(c => if(c.picture.equals(Picture.SIX)){i+=1})
                i should be(4)
            }
            "have 4 SEVENS" in {
                var i = 0
                cards.foreach(c => if(c.picture.equals(Picture.SEVEN)){i+=1})
                i should be(4)
            }
            "have 4 EIGHTS" in {
                var i = 0
                cards.foreach(c => if(c.picture.equals(Picture.EIGHT)){i+=1})
                i should be(4)
            }
            "have 4 NINES" in {
                var i = 0
                cards.foreach(c => if(c.picture.equals(Picture.NINE)){i+=1})
                i should be(4)
            }
            "have 4 TENS" in {
                var i = 0
                cards.foreach(c => if(c.picture.equals(Picture.TEN)){i+=1})
                i should be(4)
            }
            "have 4 JACKS" in {
                var i = 0
                cards.foreach(c => if(c.picture.equals(Picture.JACK)){i+=1})
                i should be(4)
            }
            "have 4 QUEENS" in {
                var i = 0
                cards.foreach(c => if(c.picture.equals(Picture.QUEEN)){i+=1})
                i should be(4)
            }
            "have 4 KINGS" in {
                var i = 0
                cards.foreach(c => if(c.picture.equals(Picture.KING)){i+=1})
                i should be(4)
            }
            "have 4 ACES" in {
                var i = 0
                cards.foreach(c => if(c.picture.equals(Picture.ACE)){i+=1})
                i should be(4)
            }
        }
    }
}
