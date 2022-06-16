package poker
package util

import Combination._
import model.card.CardInterface

object CombinationObject:

  def findCombination(hand: Array[CardInterface]): (Option[Combination], Option[Array[CardInterface]]) =
    var combination: Option[Combination] = None
    var leftCards: Option[Array[CardInterface]] = None
    if (hasRoyalFlush(hand))
      combination = Some(ROYAL_FLUSH)
    else if (hasStraigthFlush(hand))
      combination = Some(STRAIGHT_FLUSH)
    else if (hasFourOfAKind(hand)._1)
      combination = Some(FOUR_OF_A_KIND)
      leftCards = Some(Array(hasFourOfAKind(hand)._2))
    else if (hasFullHouse(hand))
      combination = Some(FULL_HOUSE)
    else if (hasFlush(hand))
      combination = Some(FLUSH)
    else if (hasStraight(hand))
      combination = Some(STRAIGHT)
    else if (hasThreeOfAKind(hand)._1)
      combination = Some(THREE_OF_A_KIND)
      leftCards = Some(hasThreeOfAKind(hand)._2)
    else if (hasTwoPair(hand)._1)
      combination = Some(TWO_PAIR)
      leftCards = Some(Array(hasTwoPair(hand)._2))
    else if (hasPair(hand)._1)
      combination = Some(PAIR)
      leftCards = Some(hasPair(hand)._2)
    else 
      combination = Some(NOTHING)
    (combination, leftCards)

  def hasPair(hand: Array[CardInterface]): (Boolean, Array[CardInterface]) =
    var result = false
    var cardsToReturn = hand.clone
    hand.foreach(c =>
      if (hand.filter(_.picture == c.picture).length == 2 && !result)
        cardsToReturn = hand.filter(_.picture != c.picture)
        result = true
    )
    (result, cardsToReturn)

  def hasTwoPair(hand: Array[CardInterface]): (Boolean, CardInterface) =
    val checkPair = hasPair(hand)
    val checkSecondPair = hasPair(checkPair._2)
    (checkPair._1 && checkSecondPair._1, checkSecondPair._2(0))

  def hasThreeOfAKind(hand: Array[CardInterface]): (Boolean, Array[CardInterface]) =
    var result = false
    var cardsToReturn = hand.clone
    hand.foreach(c =>
      if (hand.filter(_.picture == c.picture).length == 3 && !result)
        cardsToReturn = hand.filter(_.picture != c.picture)
        result = true
    )
    (result, cardsToReturn)

  def hasStraight(hand: Array[CardInterface]): Boolean =
    var hasStraight = true
    val sortedHand = hand.sortWith(_.value < _.value)
    sortedHand.foreach(c =>
      if (sortedHand.indexOf(c) < 4)
        if (c.value + 1 != sortedHand(sortedHand.indexOf(c) + 1).value)
          hasStraight = false
    )
    hasStraight

  def hasFlush(hand: Array[CardInterface]): Boolean =
    var result = false
    hand.foreach(c =>
      if (hand.filter(_.symbol == c.symbol).length == 5)
        result = true
    )
    result

  def hasFullHouse(hand: Array[CardInterface]): Boolean =
    val tuple = hasThreeOfAKind(hand)
    tuple._1 && hasPair(tuple._2)._1

  def hasFourOfAKind(hand: Array[CardInterface]): (Boolean, CardInterface) =
    var result = false
    var cardToReturn = hand.clone
    hand.foreach(c =>
      if (hand.filter(_.picture == c.picture).length == 4 && !result)
        result = true
        cardToReturn = hand.filter(_.picture != c.picture)
    )
    (result, cardToReturn(0))

  def hasStraigthFlush(hand: Array[CardInterface]): Boolean =
    hasStraight(hand) && hasFlush(hand)

  def hasRoyalFlush(hand: Array[CardInterface]): Boolean =
    hasStraigthFlush(hand) && hand.sortWith(_.value < _.value)(4).value == 14
