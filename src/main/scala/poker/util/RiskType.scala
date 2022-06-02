package poker
package util

import model.Combination
import model.Round
import model.Card

trait RiskType :
    def message: String
    def setBet(bet: Int, credit: Int): Int
    def checkCombination(hand: Array[Card]): Option[Combination]



    
