package poker

object start {

    private var attribut : String = ""

    @main def run: Unit = {
    attribut = "Hallo"
    println("Welcome to Poker")
    println(attribut)
    val x = new Array[String](3)
    x(0) = "a"
    x(1) = "b"
    x(2) = "c"
    x(0) = "x"
    println(x(0))
    println("\u2764")
    val cards = createCards
    cards.foreach(c => println(c.toString))
}
}

enum Picture {
  case TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE,
        TEN, JACK, QUEEN, KING, ACE 

  override def toString = {
    this match {
      case TWO => "2" case THREE=> "3" case FOUR => "4" case FIVE => "5"
      case SIX=> "6" case SEVEN => "7" case EIGHT => "8"  case NINE=> "9"
      case TEN => "10" case JACK => "J" case QUEEN => "Q" case KING=> "K"
      case ACE => "A"
    }
  }
}

enum Symbol {
  case HEART, DIAMOND, CLUB, SPADE
}
case class Card(symbol: Symbol, picture: Picture){
  override def toString = picture.toString + "  (" + symbol.toString + ")"
}
def createCards: Array[Card] = {
  val cards = new Array[Card](1)
  cards(0) = new Card(Symbol.HEART, Picture.ACE)
  cards
}
