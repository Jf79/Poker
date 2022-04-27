package poker.model
import model._

case class Player (hand: Array[Card], money: Int) {
    def this(money:Int) = this(new Array[Card](5), money)
    def setHand(hand: Array[Card]):Player = new Player(hand, this.money)
    override def toString = "Money: " + money
}