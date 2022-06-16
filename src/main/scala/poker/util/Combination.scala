package poker
package util

enum Combination(multFactor: Int, rank: Int, name: String):
  
  def getMultFactor: Int = multFactor
  def getName: String = name
  def getRank: Int = rank

  case NOTHING extends Combination(-1, 10, "")
  case PAIR extends Combination(1, 9, "Pair")
  case TWO_PAIR extends Combination(2, 8, "Two Pair")
  case THREE_OF_A_KIND extends Combination(3, 7, "Three of a Kind")
  case STRAIGHT extends Combination(4, 6, "Straight")
  case FLUSH extends Combination(6, 5, "Flush")
  case FULL_HOUSE extends Combination(9, 4, "Full House")
  case FOUR_OF_A_KIND extends Combination(25, 3, "Four of a Kind")
  case STRAIGHT_FLUSH extends Combination(50, 2, "Straight Flush")
  case ROYAL_FLUSH extends Combination(250, 1, "Royal Flush")
