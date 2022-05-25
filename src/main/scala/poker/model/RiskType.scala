package poker
package model

import util.RiskType

case class HighRisk() extends RiskType :
    
    val MIN_BET = 30

    def this(credit: Int) = 
        this()
        if(credit < MIN_BET) throw new Exception("\nYou don't have enough credit to play HighRisk.\n")
        
     
    override def message: String = "High Risk Round !"
    
    override def setBet(bet: Int, credit: Int): Int = 
        if(credit < bet) throw new Exception("\nYou dont have enough credit.\nPlease reduce your bet.\n")
        if(bet >= MIN_BET) return bet
        MIN_BET


case class LowRisk() extends RiskType :
    override def message: String = "Low Risk Round !"

    override def setBet(bet : Int, credit: Int): Int = 
        if(credit < bet) 
            throw new Exception("\nYou dont have enough credit.\nPlease reduce your bet.")
        bet

object RiskType :
    def apply (game: String, credit: Int) = 
        game match {
            case "low" => new LowRisk()
            case "high" => new HighRisk(credit)
        }


        
    