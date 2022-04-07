@main def run: Unit = {
  println("Welcome to Poker")
  println("Test")
  val cards = createCards
  cards.foreach(c => println(c.toString))
}

def createCards: Array[Card] = {
  val cards = new Array[Card](5)
  cards(0) = new Card("A", "Heart")
  cards(1) = new Card("K", "Heart")
  cards(2) = new Card("Q", "Heart")
  cards(3) = new Card("J", "Heart")
  cards(4) = new Card("10", "Heart")
  cards
}

case class Card(picture: String, symbol: String) {
  override def toString = picture + "(" + symbol + ")"
}
