package poker
package model

import CombinationObject._
import Combination._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CombinationObjectSpec extends AnyWordSpec with Matchers {

  "The method findCombination" when {
    val card1 = new Card(Symbol.HEART, Picture.FOUR, 4)
    val card2 = new Card(Symbol.SPADE, Picture.FOUR, 4)
    val card3 = new Card(Symbol.DIAMOND, Picture.KING, 13)
    val card4 = new Card(Symbol.HEART, Picture.FIVE, 5)
    val card5 = new Card(Symbol.SPADE, Picture.SIX, 6)
    val card = new Card(Symbol.SPADE, Picture.SEVEN, 7)
    "the hand has a valid combination" should {
      val hand = Array(card1, card2, card3, card4, card5)
      "return some" in {
        findCombination(hand) should be(Some(PAIR))
      }
    }
    "the hand hasn't a valid combination" should {
      val hand = Array(card, card2, card3, card4, card5)
      "return none" in {
        findCombination(hand) should be(None)
      }
    }
  }

  "The method hasPair" when {
    val card1 = new Card(Symbol.HEART, Picture.FOUR, 4)
    val card2 = new Card(Symbol.SPADE, Picture.FOUR, 4)
    val card3 = new Card(Symbol.DIAMOND, Picture.KING, 13)
    val card4 = new Card(Symbol.HEART, Picture.FIVE, 5)
    val card5 = new Card(Symbol.SPADE, Picture.SIX, 6)
    val card = new Card(Symbol.SPADE, Picture.SEVEN, 7)
    "the hand has a pair" should {
      val hand = Array(card1, card2, card3, card4, card5)
      val tuple = hasPair(hand)
      "return true" in {
        tuple._1 should be(true)
      }
      "return a hand with a length decreased by 2" in {
        tuple._2.length should be(3)
      }
    }
    "the hand hasn't a pair" should {
      val hand = Array(card1, card, card3, card4, card5)
      val tuple = hasPair(hand)
      "return false" in {
        tuple._1 should be(false)
      }
      "return a hand with the same length" in {
        tuple._2.length should be(5)
      }
    }
  }

  "The method hasTwoPair" when {
    val card1 = new Card(Symbol.HEART, Picture.FOUR, 4)
    val card2 = new Card(Symbol.SPADE, Picture.FOUR, 4)
    val card3 = new Card(Symbol.DIAMOND, Picture.KING, 13)
    val card4 = new Card(Symbol.HEART, Picture.FIVE, 5)
    val card5 = new Card(Symbol.SPADE, Picture.FIVE, 5)
    val card = new Card(Symbol.SPADE, Picture.SEVEN, 7)
    "the hand has two pair" should {
      val hand = Array(card1, card2, card3, card4, card5)
      val tuple = hasTwoPair(hand)
      "return true" in {
        tuple._1 should be(true)
      }
      "return the only remaining card" in {
        tuple._2 should be(card3)
      }
    }
    "the hand hasn't two pair" should {
      val hand = Array(card1, card, card3, card4, card5)
      val tuple = hasTwoPair(hand)
      "return true" in {
        tuple._1 should be(false)
      }
      "return the first card" in {
        tuple._2 should be(card1)
      }
    }
  }

  "The method hasThreeOfAKind" when {
    val card1 = new Card(Symbol.HEART, Picture.FOUR, 4)
    val card2 = new Card(Symbol.SPADE, Picture.FIVE, 5)
    val card3 = new Card(Symbol.DIAMOND, Picture.KING, 13)
    val card4 = new Card(Symbol.HEART, Picture.FIVE, 5)
    val card5 = new Card(Symbol.SPADE, Picture.FIVE, 5)
    val card = new Card(Symbol.SPADE, Picture.SEVEN, 7)
    "the hand has three of a kind" should {
      val hand = Array(card1, card2, card3, card4, card5)
      val tuple = hasThreeOfAKind(hand)
      "return true" in {
        tuple._1 should be(true)
      }
      "return a 2 remaining cards" in {
        tuple._2.length should be(2)
      }
    }
    "the hand hasn't three of a kind" should {
      val hand = Array(
        card1,
        new Card(Symbol.SPADE, Picture.SEVEN, 7),
        card3,
        card4,
        card5
      )
      val tuple = hasThreeOfAKind(hand)
      "return true" in {
        tuple._1 should be(false)
      }
      "return the whole hand" in {
        tuple._2 should be(hand)
      }
    }
  }

  "The method hasStraight" when {
    val card1 = new Card(Symbol.HEART, Picture.TWO, 2)
    val card2 = new Card(Symbol.CLUB, Picture.THREE, 3)
    val card3 = new Card(Symbol.DIAMOND, Picture.FIVE, 5)
    val card4 = new Card(Symbol.HEART, Picture.FOUR, 4)
    val card5 = new Card(Symbol.SPADE, Picture.SIX, 6)
    val card = new Card(Symbol.DIAMOND, Picture.KING, 13)
    "the hand has a straight" should {
      val hand = Array(card1, card2, card3, card4, card5)
      "return true" in {
        hasStraight(hand) should be(true)
      }
    }
    "the hand hasn't a straight" should {
      val hand = Array(card, card2, card3, card4, card5)
      "return false" in {
        hasStraight(hand) should be(false)
      }
    }
  }

  "The method hasFlush" when {
    val card1 = new Card(Symbol.HEART, Picture.TWO, 2)
    val card2 = new Card(Symbol.HEART, Picture.THREE, 3)
    val card3 = new Card(Symbol.HEART, Picture.JACK, 11)
    val card4 = new Card(Symbol.HEART, Picture.NINE, 9)
    val card5 = new Card(Symbol.HEART, Picture.SIX, 6)
    val card = new Card(Symbol.DIAMOND, Picture.KING, 13)
    "the hand has a flush" should {
      val hand = Array(card1, card2, card3, card4, card5)
      "return true" in {
        hasFlush(hand) should be(true)
      }
    }
    "the hand hasn't a flush" should {
      val hand = Array(card, card2, card3, card4, card5)
      "return true" in {
        hasFlush(hand) should be(false)
      }
    }
  }

  "The method hasFullHouse" when {
    val card1 = new Card(Symbol.HEART, Picture.FOUR, 4)
    val card2 = new Card(Symbol.CLUB, Picture.FIVE, 5)
    val card3 = new Card(Symbol.DIAMOND, Picture.FOUR, 4)
    val card4 = new Card(Symbol.HEART, Picture.FIVE, 5)
    val card5 = new Card(Symbol.SPADE, Picture.FIVE, 5)
    val card = new Card(Symbol.DIAMOND, Picture.KING, 13)
    "the hand has a full house" should {
      val hand = Array(card1, card2, card3, card4, card5)
      "return true" in {
        hasFullHouse(hand) should be(true)
      }
    }
    "the hand hasn't a full house" should {
      val hand = Array(card, card2, card3, card4, card5)
      "return false" in {
        hasFullHouse(hand) should be(false)
      }
    }
  }

  "The method hasFourOfAKind" when {
    val card1 = new Card(Symbol.CLUB, Picture.FIVE, 5)
    val card2 = new Card(Symbol.SPADE, Picture.FIVE, 5)
    val card3 = new Card(Symbol.DIAMOND, Picture.KING, 13)
    val card4 = new Card(Symbol.HEART, Picture.FIVE, 5)
    val card5 = new Card(Symbol.DIAMOND, Picture.FIVE, 5)
    val card = new Card(Symbol.SPADE, Picture.SEVEN, 7)
    "the hand has four of a kind" should {
      val hand = Array(card1, card2, card3, card4, card5)
      val tuple = hasFourOfAKind(hand)
      "return true" in {
        tuple._1 should be(true)
      }
      "return the remaining card" in {
        tuple._2 should be(card3)
      }
    }
    "the hand hasn't four of a kind" should {
      val hand = Array(card, card2, card3, card4, card5)
      val tuple = hasFourOfAKind(hand)
      "return false" in {
        tuple._1 should be(false)
      }
      "return the first card" in {
        tuple._2 should be(card)
      }
    }
  }

  "The method hasStraigthFlush" when {
    val card1 = new Card(Symbol.HEART, Picture.TWO, 2)
    val card2 = new Card(Symbol.HEART, Picture.THREE, 3)
    val card3 = new Card(Symbol.HEART, Picture.FIVE, 5)
    val card4 = new Card(Symbol.HEART, Picture.FOUR, 4)
    val card5 = new Card(Symbol.HEART, Picture.SIX, 6)
    val card = new Card(Symbol.SPADE, Picture.SEVEN, 7)
    "the hand has a straigth flush" should {
      val hand = Array(card1, card2, card3, card4, card5)
      "return true" in {
        hasStraigthFlush(hand) should be(true)
      }
    }
    "the hand hasn't a straigth flush" should {
      val hand = Array(card, card2, card3, card4, card5)
      "return true" in {
        hasStraigthFlush(hand) should be(false)
      }
    }
  }

  "The method RoyalFlush" when {
    val card1 = new Card(Symbol.HEART, Picture.TEN, 10)
    val card2 = new Card(Symbol.HEART, Picture.ACE, 14)
    val card3 = new Card(Symbol.HEART, Picture.QUEEN, 12)
    val card4 = new Card(Symbol.HEART, Picture.JACK, 11)
    val card5 = new Card(Symbol.HEART, Picture.KING, 13)
    val card = new Card(Symbol.SPADE, Picture.SEVEN, 7)
    "the hand has a royal flush" should {
      val hand = Array(card1, card2, card3, card4, card5)
      "return true" in {
        hasRoyalFlush(hand) should be(true)
      }
    }
    "the hand hasn't a royal flush" should {
      val hand = Array(card, card2, card3, card4, card5)
      "return true" in {
        hasRoyalFlush(hand) should be(false)
      }
    }
  }
}
