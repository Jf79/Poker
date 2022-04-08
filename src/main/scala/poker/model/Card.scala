package model

case class Card(symbol: Symbol, picture: Picture, value: Integer) {
    override def toString = picture.toString + " (" + symbol + ")"
}

