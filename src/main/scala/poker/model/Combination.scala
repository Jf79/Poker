package poker.model

enum Combination(multFactor: Int):
    case PAIR extends Combination(1)
    case TWO_PAIR extends Combination(2)
    case THREE_OF_A_KIND extends Combination(3)
    case STRAIGHT  extends Combination(4)
    case FLUSH extends Combination(6)
    case FULL_HOUSE extends Combination(9)
    case FOUR_OF_A_KIND extends Combination(10)