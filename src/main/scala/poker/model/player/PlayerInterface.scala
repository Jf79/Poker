package poker
package model
package player

trait PlayerInterface :
    def addMoney(m: Int): Int
    def getMoney(): Int

object CreatePlayer :
    def apply (money: Int) = new Player(money)

