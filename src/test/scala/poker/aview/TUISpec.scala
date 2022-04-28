package poker
package view

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import model._
import model.CardsObject._
import poker.model._
import view.TUI

class TUISpec extends AnyWordSpec with Matchers { 
   /* 
    "The method holdCards(deck: Array[Card], holdedCards: Array[Int], player: Player)" when {
        "its called with completly holded cards " should {
            var deck = createCards()
            val tuple = getRandomCards(deck, 5)
            val player = new Player(tuple._1, 100)
            deck = tuple._2
            "return the exact hand again" in {
                
            }
        }
    }

    "The method checkinput(input:String)" when {
        "its called with argument ('c')" should {
            "return true" in {
               val tui = new TUI()
               tui.checkInput("c") should be(true)
            }
        }
    }

    "A deck" when {
        "its created with createDeck()" should {
            val tui = new TUI()
            val deck = tui.createDeck()
            "be the same like Game.createCards()" in {
                deck should be(createCards())
            }
        }
    }
    "The TUI" when {
        "it prints the game it" should {
            val tui = new TUI()
            val cards = Array(
            new Card(Symbol.HEART, Picture.TWO , 2),
            new Card(Symbol.HEART, Picture.THREE , 3),
            new Card(Symbol.HEART, Picture.FOUR , 4),
            new Card(Symbol.HEART, Picture.FIVE , 5),
            new Card(Symbol.HEART, Picture.SIX , 6))
            val game = tui.printGame(new Player(cards, 100))
            "print numbers and cards" in {
                val test = "\n\n[1]\t\t[2]\t\t[3]\t\t[4]\t\t[5]\t\t\n\n" +
                "2 (HEART)\t3 (HEART)\t4 (HEART)\t5 (HEART)\t6 (HEART)\t\n\n"
                game should be(test)
            }
        }
        "it prints the numbers it" should {
            val tui = new TUI()
            val numbers = tui.printNumbers()
            "be 1   2   3   4   5" in {
                val test = "[1]\t\t[2]\t\t[3]\t\t[4]\t\t[5]\t\t"
                test should be(numbers)
            }
        }
        "it prints the cards it" should {
            val tui = new TUI()
            val cards = tui.printCards(
            Array(
            new Card(Symbol.HEART, Picture.TWO , 2),
            new Card(Symbol.HEART, Picture.THREE , 3),
            new Card(Symbol.HEART, Picture.FOUR , 4),
            new Card(Symbol.HEART, Picture.FIVE , 5),
            new Card(Symbol.HEART, Picture.SIX , 6))
            )
            "be 2 (HEART) 3 (HEART) 4 (HEART) 5 (HEART) 6 (HEART)" in {
                cards should be("2 (HEART)\t3 (HEART)\t4 (HEART)\t5 (HEART)\t6 (HEART)\t")
            }
        }
    }*/
}