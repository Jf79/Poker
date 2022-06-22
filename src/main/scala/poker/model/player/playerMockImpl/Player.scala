package poker
package model
package player
package playerMockImpl

case class Player(var money: Int) extends PlayerInterface:
  override def addMoney(m: Int): Int = 0
  override def getMoney(): Int = 0
  override def toString: String = null
