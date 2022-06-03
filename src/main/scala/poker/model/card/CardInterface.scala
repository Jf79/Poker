package poker
package model
package card

import util.Symbol
import util.Picture

trait CardInterface:
    val symbol: Symbol
    val picture: Picture
    val value: Int

object CreateCard :
    def apply (s: Symbol, p: Picture, v: Int) = new Card(s, p, v)
