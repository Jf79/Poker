package poker
package model

import util.Symbol
import util.Picture
import poker.model.card.CardInterface

case class Card(symbol: Symbol, picture: Picture, value: Int) extends CardInterface:

  override def toXml(): String = null

  override def toString = "" + picture.toString + " (" + symbol.toString + ")"

