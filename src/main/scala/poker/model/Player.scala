package poker
package model

case class Player(money: Int):
  override def toString = "Remaining money: " + money
