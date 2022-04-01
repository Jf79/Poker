@main def hello: Unit = {
    val card1 = new Card("A","Heart")
    val card2 = new Card("K","Heart")
    val card3 = new Card("Q","Heart")
    val card4 = new Card("J","Heart")
    val card5 = new Card("10","Heart")

    println("Hello world!")
    println(msg)
}

case class Card(picture:String, symbol:String) {
    override def toString = picture + "("+symbol+")"
}

def msg = "I was compiled by Scala 3. :)"


