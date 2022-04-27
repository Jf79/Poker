package poker.model
import Combination._

object CombinationObject {

    def findCombination(hand: Array[Card]): Combination =
        null

    def hasPair(hand: Array[Card]): (Boolean, Array[Card]) =
        null
    
    def hasTwoPair(hand: Array[Card]): (Boolean, Card) =
        null

    def hasThreeOfAKind(hand: Array[Card]): (Boolean, Array[Card]) =
        null
    
    def hasStraight(hand: Array[Card]): Boolean =
        false

    def hasFlush(hand: Array[Card]): Boolean =
        false
    
    def hasFullHouse(hand: Array[Card]): Boolean =
        false

    def hasFourOfAKind(hand: Array[Card]): (Boolean, Card) =
        null
    
    def hasStraigthFlush(hand: Array[Card]): Boolean =
        false

    def hasRoyalFlush(hand : Array[Card]): Boolean = 
        false
}