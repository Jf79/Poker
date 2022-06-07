package poker
package model
package round

import util.RiskType
import util.Combination
import util.CombinationObject._
import card.CardInterface


case class HighRisk() extends RiskType :
    
    val MIN_BET = 10

    def this(credit: Int) = 
        this()
        if(credit < MIN_BET) throw new Exception("You don't have\nenough credit to\nplay HighRisk")
        
    override def lowestCombination(): String = "Pair"

    override def message: String = "High Risk Round !"
    
    override def setBet(bet: Int, credit: Int): Int = 
        if(credit < bet) throw new Exception("\nYou dont have enough credit.\nPlease reduce your bet.\n")
        if(bet >= MIN_BET) return bet
        MIN_BET

    override def checkCombination(hand: Array[CardInterface]): Option[Combination] = 
        val c = findCombination(hand)._1
        if(!c.isEmpty) return c
        None
    

case class LowRisk() extends RiskType :
    override def message: String = "Low Risk Round !"
    override def lowestCombination(): String = "Jacks or Better"

    override def setBet(bet : Int, credit: Int): Int = 
        if(credit < bet) 
            throw new Exception("\nYou dont have enough credit.\nPlease reduce your bet.")
        bet

    override def checkCombination(hand: Array[CardInterface]): Option[Combination] = 
        val comb = findCombination(hand)
        val leftCards = comb._2
        if(!comb._1.isEmpty && !leftCards.isEmpty)
            if(isUsefulPair(comb._1.get, hand, comb._2.get))
                return None
        comb._1
    
    def isUsefulPair(c: Combination, hand: Array[CardInterface], leftCards: Array[CardInterface]) : Boolean = 
        if(c.equals(Combination.PAIR))
            val pair = hand.filterNot(leftCards.contains(_))
            if(pair(0).value < 11)
                return false
        true
            

object RiskType :
    def apply (game: String, credit: Int) = 
        game match {
            case "high" => new HighRisk(credit)
            case _ => new LowRisk()
        }


        
    