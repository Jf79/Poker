package poker
package model
package player
package playerBaseImpl

import scala.xml._

case class Player(var money: Int) extends PlayerInterface:
  override def addMoney(m: Int): Int = 
    money = money + m
    money
  override def getMoney(): Int = money
  override def toString = "Remaining money: " + money
