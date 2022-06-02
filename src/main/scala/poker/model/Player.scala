package poker
package model

case class Player(var money: Int):
  override def toString = "Remaining money: " + money
