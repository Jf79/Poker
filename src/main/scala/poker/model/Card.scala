package poker
package model

import util.Symbol
import util.Picture

case class Card(symbol: Symbol, picture: Picture, value: Integer) {
  override def toString = "" + picture.toString + " (" + symbol.toString + ")"
}
