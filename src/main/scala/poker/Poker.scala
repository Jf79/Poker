package poker
import model._

object start {

    @main def run: Unit = {
    println("Welcome to Poker")
    
    val card = new Card(Symbol.HEART, Picture.TWO , 2) 
    println(card)
    
  }
}

