package poker
package model

import util.Type

case class HighRisk() extends Type :
    override def message: String = "High Risk Round !"

case class LowRisk() extends Type :
    override def message: String = "Low Risk Round !"

object Type :
    def apply (game: String) = 
        game match {
            case "low" => new LowRisk()
            case "high" => new HighRisk()
        }


        
    