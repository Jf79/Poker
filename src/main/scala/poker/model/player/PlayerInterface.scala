package poker
package model
package player

import playerBaseImpl.Player 
import scala.xml._

trait PlayerInterface :
    def addMoney(m: Int): Int
    def getMoney(): Int

object CreatePlayer :
    def apply (money: Int): PlayerInterface = new Player(money)

