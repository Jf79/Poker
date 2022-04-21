package poker.view

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import model._
import view.TUI

class TUISpec extends AnyWordSpec with Matchers { 
    "A deck" when {
        "its created with createDeck()" should {
            val tui = new TUI()
            val deck = tui.createDeck()
            "be the same like Game.createCards()" in {
                deck should be(Game.createCards())
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
                val test = "\n\n[1]\t\t[2]\t\t[3]\t\t[4]\t\t[5]\t\t\n" +
                "2 (HEART)\t3 (HEART)\t4 (HEART)\t5 (HEART)\t6 (HEART)\t"
                game should be(test)
            }
        }
        "it prints the numbers it" should {
            val tui = new TUI()
            val numbers = tui.printNumbers()
            "be 1   2   3   4   5" in {
                val test = "\n[1]\t\t[2]\t\t[3]\t\t[4]\t\t[5]\t\t"
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
                cards should be("\n2 (HEART)\t3 (HEART)\t4 (HEART)\t5 (HEART)\t6 (HEART)\t")
            }
        }
    }
}