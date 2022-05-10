package poker.model

import Combination._

object CombinationObject :

  def findCombination(hand: Array[Card]): Option[Combination] =
    ???

  def hasPair(hand: Array[Card]): (Boolean, Array[Card]) =
    var result = false
    var cardsToReturn = hand
    hand.foreach(c => 
        if (hand.filter(_.picture == c.picture).length == 2 && !result)
            cardsToReturn = hand.filter(_.picture != c.picture) 
            result = true
    )
    (result, cardsToReturn)


  def hasTwoPair(hand: Array[Card]): (Boolean, Card) =
    val checkPair = hasPair(hand)
    val checkSecondPair = hasPair(checkPair._2)
    (checkPair._1 && checkSecondPair._1, checkSecondPair._2(0))


  def hasThreeOfAKind(hand: Array[Card]): (Boolean, Array[Card]) =
    var result = false
    var cardsToReturn = hand
    hand.foreach(c =>
        if (hand.filter(_.picture == c.picture).length == 3 && !result) 
            cardsToReturn = hand.filter(_.picture != c.picture)
            result = true
    )
    (result, cardsToReturn)


  def hasStraight(hand: Array[Card]): Boolean =
    var hasStraight = true
    val sortedHand = hand.sortWith(_.value < _.value)
    sortedHand.foreach(c => 
        if(sortedHand.indexOf(c) < 4)
            if(c.value + 1 != sortedHand(sortedHand.indexOf(c) + 1).value)
                hasStraight = false
    )
    hasStraight


  def hasFlush(hand: Array[Card]): Boolean =
    var result = false
    hand.foreach(c =>
        if (hand.filter(_.symbol == c.symbol).length == 5)
            result = true
    )
    result


  def hasFullHouse(hand: Array[Card]): Boolean =
    val tuple = hasThreeOfAKind(hand)
    tuple._1 && hasPair(tuple._2)._1


  def hasFourOfAKind(hand: Array[Card]): (Boolean, Card) =
    var result = false
    var cardToReturn = hand
    hand.foreach(c =>
        if (hand.filter(_.picture == c.picture).length == 4 && !result)
            result = true
            cardToReturn = hand.filter(_.picture != c.picture)
    )
    (result, cardToReturn(0))


  def hasStraigthFlush(hand: Array[Card]): Boolean =
    hasStraight(hand) && hasFlush(hand)


  def hasRoyalFlush(hand: Array[Card]): Boolean =
    hasStraigthFlush(hand) && hand.sortWith(_.value < _.value)(4).value == 14
