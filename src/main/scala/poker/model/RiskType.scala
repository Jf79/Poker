package poker
package model

import util.RiskType

case class HighRisk() extends RiskType :
    override def message: String = "High Risk Round !"
    
    override def setBet(bet : Int): Int = 
        if(bet >= 30) 
            return bet
        return 30

case class LowRisk() extends RiskType :
    override def message: String = "Low Risk Round !"

    override def setBet(bet : Int): Int = bet

object RiskType :
    def apply (game: String) = 
        game match {
            case "low" => new LowRisk()
            case "high" => new HighRisk()
        }


        
    