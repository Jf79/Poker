package poker
package model
package round
package roundBaseImpl

import util.RiskType
import util.Combination
import util.CombinationObject._
import card.CardInterface


case class HighRisk() extends RiskType :
    
    val MIN_BET = 10

    def this(credit: Int) = 
        this()
        if(credit < MIN_BET) throw new Exception("You don't have\nenough credit to\nplay HighRisk")
        
    override def lowestCombination: String = "Pair"

    override def getMinimumBet: Int = MIN_BET
    override def message: String = "High Risk Round !"
    
    override def setBet(bet: Int, credit: Int): Int = 
        if(credit < bet) throw new Exception("You dont have\nenough credit\nreduce your Bet")
        if(bet >= MIN_BET) return bet
        MIN_BET

    override def checkCombination(hand: Array[CardInterface]):(Option[Combination], Option[Array[CardInterface]]) = 
        val tuple = findCombination(hand)
        (tuple._1, filterCombination(tuple, Some(hand)))
    
    def filterCombination(tuple: (Option[Combination], Option[Array[CardInterface]]), hand: Option[Array[CardInterface]]) : Option[Array[CardInterface]]=
        if(!tuple._1.get.equals(Combination.NOTHING))
            var leftCards: Option[Array[CardInterface]] = None
            if(!tuple._2.isEmpty)
                leftCards = tuple._2
            else
                leftCards = Some(Array())
            return Some(hand.get.filterNot(leftCards.get.contains(_)))
        None
    
    override def toString(): String = "high"
    

case class LowRisk() extends RiskType :
    override def message: String = "Low Risk Round !"
    
    override def lowestCombination: String = "Jacks or Better"

    override def getMinimumBet: Int = 1


    override def setBet(bet : Int, credit: Int): Int = 
        if(credit < bet) 
            throw new Exception("You dont have\nenough credit\nreduce your Bet")
        bet

    override def checkCombination(hand: Array[CardInterface]): (Option[Combination], Option[Array[CardInterface]]) =
        val tuple = findCombination(hand)
        val comb = filterCombination(tuple, Some(hand))
        if(comb.isEmpty)
            return (Some(Combination.NOTHING), comb)
        (tuple._1, comb)
        
    def filterCombination(tuple: (Option[Combination], Option[Array[CardInterface]]), hand: Option[Array[CardInterface]]) : Option[Array[CardInterface]]=
        if(!tuple._1.get.equals(Combination.NOTHING))
            var leftCards: Option[Array[CardInterface]] = None
            if(!tuple._2.isEmpty)
                leftCards = tuple._2
            else
                leftCards = Some(Array())
            val combHand = hand.get.filterNot(leftCards.get.contains(_))
            if(tuple._1.get.equals(Combination.PAIR))
                if(combHand(0).value < 11)
                    return None
            return Some(combHand)
        None
    
    override def toString(): String = "low"

            

object RiskType :
    def apply (game: String, credit: Int) = 
        game match {
            case "high" => new HighRisk(credit)
            case _ => new LowRisk()
        }


        
    