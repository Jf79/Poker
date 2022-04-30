package poker.model
import Combination._

object CombinationObject {

  def findCombination(hand: Array[Card]): Combination =
    null

  def hasPair(hand: Array[Card]): (Boolean, Array[Card]) =
    var result = false
    var index = 0
    hand.foreach(c =>
      val pictures = hand.filter(_.picture == c.picture)
      if (pictures.length == 2) then {
        result = true
        index = hand.indexOf(c)
      }
    )
    val cardsToReturn = hand.filter(_.picture != hand(index).picture)
    (result, cardsToReturn)

  def hasTwoPair(hand: Array[Card]): (Boolean, Card) =
    val checkFirst = hasPair(hand)
    val checkSecond = hasPair(checkFirst._2)
    var result = checkFirst._1 && checkSecond._1
    (result, checkSecond._2(0))

  def hasThreeOfAKind(hand: Array[Card]): (Boolean, Array[Card]) =
    var result = false
    var index = 0
    hand.foreach(c =>
      val pictures = hand.filter(_.picture == c.picture)
      if (pictures.length == 3) then {
        result = true
        index = hand.indexOf(c)
      }
    )
    val cardsToReturn = hand.filter(_.picture != hand(index).picture)
    (result, cardsToReturn)

  def hasStraight(hand: Array[Card]): Boolean =
    var result = false
    val sortedHand = hand.sortWith(_.value < _.value)
    val valueFirst = sortedHand(0).value
    val valueLast = sortedHand(sortedHand.length - 1).value
    val diff = valueLast - valueFirst
    if (diff == hand.length - 1) result = true
    result

  def hasFlush(hand: Array[Card]): Boolean =
    var result = false

    hand.foreach(c =>
      val symbols = hand.filter(_.symbol == c.symbol)
      if (symbols.length == 5) then {
        result = true
      }
    )
    result

  def hasFullHouse(hand: Array[Card]): Boolean =
    var result = false
    val tuple1 = hasThreeOfAKind(hand)
    val tuple2 = hasPair(tuple1._2)
    if (tuple1._1 == true && tuple2._1 == true) result = true
    result

  def hasFourOfAKind(hand: Array[Card]): (Boolean, Card) =
    var result = false
    var index = 0
    hand.foreach(c =>
      val pictures = hand.filter(_.picture == c.picture)
      if (pictures.length == 4) then {
        result = true
        index = hand.indexOf(c)
      }
    )
    val cardToReturn = hand.filter(_.picture != hand(index).picture)
    (result, cardToReturn(0))

  def hasStraigthFlush(hand: Array[Card]): Boolean =
    val checkStraight = hasStraight(hand)
    val checkFlush = hasFlush(hand)
    val result = checkStraight && checkFlush
    result

  def hasRoyalFlush(hand: Array[Card]): Boolean =
    var royal = false
    val sortedHand = hand.sortWith(_.value < _.value)
    if (sortedHand(0).value >= 10 && sortedHand(hand.length - 1).value >= 10)
      royal = true
    val checkSF = hasStraigthFlush(hand)
    val result = royal && checkSF
    result
}
