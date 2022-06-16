package poker
package util

enum Symbol:
  case HEART, DIAMOND, CLUB, SPADE

  override def toString = 
    this match
      case HEART => "Heart"
      case SPADE => "Spade"
      case DIAMOND => "Diamond"
      case CLUB => "Club"

  def paint : String = 
    this match 
      case HEART => "♥"
      case SPADE => "♠"
      case DIAMOND => "♦"
      case CLUB => "♣"

  

