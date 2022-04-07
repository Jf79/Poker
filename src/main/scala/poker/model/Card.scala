package model

case class Card(symbol: Symbol, picture: Picture){

    override def toString = picture + "(" + symbol + ")"
}

