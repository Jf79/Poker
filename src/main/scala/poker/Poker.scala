package poker
import model.Card

@main def run: Unit = {
  println("Welcome to Poker")
  println("Test")
  val cards = createCards
  cards.foreach(c => println(c.toString))
}

def createCards: Array[Card] = {
  val cards = new Array[Card](5)
  cards(0) = new Card(Picture.ACE, Symbol.HEART)
  cards
}
