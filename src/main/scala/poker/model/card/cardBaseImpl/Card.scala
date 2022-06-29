package poker
package model
package card
package cardBaseImpl

import util.Symbol
import util.Picture
import scala.xml.{NodeSeq, PrettyPrinter}

case class Card(symbol: Symbol, picture: Picture, value: Int) extends CardInterface:

  override def toString = "" + picture.toString + " (" + symbol.toString + ")"

