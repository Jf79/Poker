package poker
package util

import util.Combination
import model.card.CardInterface

trait RiskType :
    def getMinimumBet: Int
    def lowestCombination : String
    def message: String
    def setBet(bet: Int, credit: Int): Int
    def checkCombination(hand: Array[CardInterface]): Option[Combination]



    
