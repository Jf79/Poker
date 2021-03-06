package poker
package model
package card

import util.Symbol
import util.Picture


import card.cardBaseImpl.Card
import scala.xml.Elem

trait CardInterface:
    val symbol: Symbol
    val picture: Picture
    val value: Int
    def toString(): String

object CreateCard :
    def apply (s: Symbol, p: Picture, v: Int): CardInterface = new Card(s, p, v)
