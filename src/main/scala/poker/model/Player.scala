package model

case class Player (cards: Array[Card], money: Int) {
    override def toString = "Money: " + money
}