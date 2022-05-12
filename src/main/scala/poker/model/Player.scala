package poker
package model


case class Player (hand: Array[Card], money: Int) {
    
    def this(money:Int) = this(new Array[Card](1), money)
    
    def setHand(hand: Array[Card]):Player = new Player(hand, this.money)
    
    override def toString = "Remaining money: " + money
}