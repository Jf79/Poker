package poker.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import CombinationObject._

class CombinationObjectSpec extends AnyWordSpec with Matchers {
  "A hand has a pair" when {
    val card1 = new Card(Symbol.HEART, Picture.FOUR, 4)
    val card2 = new Card(Symbol.SPADE, Picture.TWO, 4)
    val card3 = new Card(Symbol.DIAMOND, Picture.KING, 13)
    val card4 = new Card(Symbol.HEART, Picture.FIVE, 5)
    val card5 = new Card(Symbol.SPADE, Picture.FOUR, 4)
    val hand = Array(card1, card2, card3, card4, card5)
    val tuple = hasPair(hand)

    "the return Value of hasPair is true " in {
      tuple._1 should be(true)
    }

    "the length of the hand is decreased by 2 or maximal 2" in {
      tuple._2.length should be(hand.length - 2)
    }

    "The pair does not longer exist in hand" in {
      var exists = false
      tuple._2.foreach(c =>
        if (c.equals(card1) || c.equals(card5)) { exists = true }
      )
      exists should be(false)
    }

  }

  "A hand has no pair" when {
    val card1 = new Card(Symbol.HEART, Picture.FOUR, 4)
    val card2 = new Card(Symbol.SPADE, Picture.SIX, 6)
    val card3 = new Card(Symbol.DIAMOND, Picture.TWO, 2)
    val card4 = new Card(Symbol.HEART, Picture.FIVE, 5)
    val card5 = new Card(Symbol.SPADE, Picture.KING, 13)
    val hand = Array(card1, card2, card3, card4, card5)
    val tuple = hasPair(hand)

    "the return Value of hasPair is false " in {
      tuple._1 should be(false)
    }
  }

  "A hand with two cards has a pair" when {
    val card1 = new Card(Symbol.HEART, Picture.FOUR, 4)
    val card2 = new Card(Symbol.DIAMOND, Picture.FOUR, 4)
    val hand = Array(card1, card2)
    val tuple = hasPair(hand)

    "The return Value should be true" in {
      tuple._1 should be(true)
    }
  }

  "A hand has two pair" when {
    val card1 = new Card(Symbol.HEART, Picture.FOUR, 4)
    val card2 = new Card(Symbol.SPADE, Picture.FIVE, 5)
    val card3 = new Card(Symbol.DIAMOND, Picture.FOUR, 4)
    val card4 = new Card(Symbol.HEART, Picture.FIVE, 5)
    val card5 = new Card(Symbol.SPADE, Picture.KING, 13)
    val hand = Array(card1, card2, card3, card4, card5)
    val tuple = hasTwoPair(hand)

    "the return value of hasTwoPair is true" in {
      tuple._1 should be(true)
    }

    "the remaining card is returned" in {
      tuple._2.equals(card5) should be(true)
    }
  }

  "A hand has not two pairs" when {
    val card1 = new Card(Symbol.HEART, Picture.FOUR, 4)
    val card2 = new Card(Symbol.SPADE, Picture.FOUR, 4)
    val card3 = new Card(Symbol.DIAMOND, Picture.TWO, 2)
    val card4 = new Card(Symbol.HEART, Picture.FIVE, 5)
    val card5 = new Card(Symbol.SPADE, Picture.KING, 13)
    val hand = Array(card1, card2, card3, card4, card5)
    val tuple = hasTwoPair(hand)

    "there is only one pair. Return should be false" in {
      tuple._1 should be(false)
    }

  }

  "A hand has three of a kind" when {
    val card1 = new Card(Symbol.HEART, Picture.FOUR, 4)
    val card2 = new Card(Symbol.CLUB, Picture.FIVE, 5)
    val card3 = new Card(Symbol.DIAMOND, Picture.FOUR, 4)
    val card4 = new Card(Symbol.HEART, Picture.FIVE, 5)
    val card5 = new Card(Symbol.SPADE, Picture.FIVE, 5)
    val hand = Array(card1, card2, card3, card4, card5)

    val tuple = hasThreeOfAKind(hand)

    "The return Value is true" in {
      tuple._1 should be(true)
    }

    "the length of the hand is decreased by 3" in {
      tuple._2.length should be(hand.length - 3)
    }

    "The hand holds no more of the picture" in {
      var exists = false
      tuple._2.foreach(d => if (d.picture == Picture.FIVE) exists = true)
      exists should be(false)
    }

  }

  "A hand has a Flush" when {
    val card1 = new Card(Symbol.HEART, Picture.FOUR, 4)
    val card2 = new Card(Symbol.HEART, Picture.TWO, 2)
    val card3 = new Card(Symbol.HEART, Picture.TEN, 10)
    val card4 = new Card(Symbol.HEART, Picture.FIVE, 5)
    val card5 = new Card(Symbol.HEART, Picture.ACE, 14)
    val hand = Array(card1, card2, card3, card4, card5)
    val result = hasFlush(hand)

    "The result is true " in {
      result should be(true)
    }
  }

  "A hand has a Full House" when {
    val card1 = new Card(Symbol.HEART, Picture.FOUR, 4)
    val card2 = new Card(Symbol.CLUB, Picture.FIVE, 5)
    val card3 = new Card(Symbol.DIAMOND, Picture.FOUR, 4)
    val card4 = new Card(Symbol.HEART, Picture.FIVE, 5)
    val card5 = new Card(Symbol.SPADE, Picture.FIVE, 5)
    val hand = Array(card1, card2, card3, card4, card5)
    val result = hasFullHouse(hand)

    "The return value of Method should be true" in {
      result should be(true)
    }

  }

  "A hand has four of a Kind" when {
    val card1 = new Card(Symbol.HEART, Picture.FOUR, 4)
    val card2 = new Card(Symbol.CLUB, Picture.FOUR, 4)
    val card3 = new Card(Symbol.DIAMOND, Picture.FOUR, 4)
    val card4 = new Card(Symbol.HEART, Picture.FIVE, 5)
    val card5 = new Card(Symbol.SPADE, Picture.FOUR, 4)
    val hand = Array(card1, card2, card3, card4, card5)
    val result = hasFourOfAKind(hand)

    "The return value of Method is true" in {
      result._1 should be(true)
    }

    "The remainig card is returned" in {
      var rightCard = result._2.equals(card4)
      rightCard should be(true)
    }
  }

  "A hand has straight" when {
    val card1 = new Card(Symbol.HEART, Picture.TWO, 2)
    val card2 = new Card(Symbol.CLUB, Picture.THREE, 3)
    val card3 = new Card(Symbol.DIAMOND, Picture.FIVE, 5)
    val card4 = new Card(Symbol.HEART, Picture.FOUR, 4)
    val card5 = new Card(Symbol.SPADE, Picture.SIX, 6)
    val hand = Array(card1, card2, card3, card4, card5)
    val result = hasStraight(hand)

    "The result of hasStraight is true" in {
      result should be(true)
    }
  }

  "A hand has Straigth flush" when {
    val card1 = new Card(Symbol.HEART, Picture.TWO, 2)
    val card2 = new Card(Symbol.HEART, Picture.THREE, 3)
    val card3 = new Card(Symbol.HEART, Picture.FIVE, 5)
    val card4 = new Card(Symbol.HEART, Picture.FOUR, 4)
    val card5 = new Card(Symbol.HEART, Picture.SIX, 6)
    val hand = Array(card1, card2, card3, card4, card5)
    val result = hasStraigthFlush(hand)

    "The result of hasStraigthFlush is true" in {
      result should be(true)
    }
  }

  "A hand has no Straigth Flush" when {
    val card1 = new Card(Symbol.HEART, Picture.TWO, 2)
    val card2 = new Card(Symbol.HEART, Picture.THREE, 3)
    val card3 = new Card(Symbol.HEART, Picture.FIVE, 5)
    val card4 = new Card(Symbol.DIAMOND, Picture.FOUR, 4)
    val card5 = new Card(Symbol.HEART, Picture.SIX, 6)
    val hand = Array(card1, card2, card3, card4, card5)
    val result = hasStraigthFlush(hand)

    "The result of hasStraigthFlush is true" in {
      result should be(false)
    }
  }

  "A hand has a RoyalFlush" when {
    val card1 = new Card(Symbol.HEART, Picture.TEN, 10)
    val card2 = new Card(Symbol.HEART, Picture.ACE, 14)
    val card3 = new Card(Symbol.HEART, Picture.QUEEN, 12)
    val card4 = new Card(Symbol.HEART, Picture.JACK, 11)
    val card5 = new Card(Symbol.HEART, Picture.KING, 13)
    val hand = Array(card1, card2, card3, card4, card5)
    val result = hasRoyalFlush(hand)

    "The result of hasRoyalFlush is true" in {
      result should be(true)
    }
  }

  "A hand has no RoyalFlush" when {
    val card1 = new Card(Symbol.HEART, Picture.TEN, 10)
    val card2 = new Card(Symbol.HEART, Picture.NINE, 9)
    val card3 = new Card(Symbol.HEART, Picture.QUEEN, 12)
    val card4 = new Card(Symbol.HEART, Picture.JACK, 11)
    val card5 = new Card(Symbol.HEART, Picture.KING, 13)
    val hand = Array(card1, card2, card3, card4, card5)
    val result = hasRoyalFlush(hand)

    "The result of hasRoyalFlush is false" in {
      result should be(false)
    }
  }

}
