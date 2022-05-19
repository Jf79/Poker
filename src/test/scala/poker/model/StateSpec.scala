package poker
package model

import CardsObject._
import org.scalatest._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class StateSpec extends AnyWordSpec with Matchers {
    val player = new Player(300)
    val deck = createCards()    
    "A StartState" when {
        val round = new Round(player, deck)
        val state = new StartState(round)
        "its created" should {
            "have a round" in {
                state.stateable should be(round)
            }
        }
        "its executed" should {
            "return a round" in {
                val newRound : Option[Round] = state.execute().asInstanceOf[Option[Round]]
                newRound should not be(None)
            }
            "return a round with a RiskType state" in {
                val newRound : Round = state.execute().get.asInstanceOf[Round]
                newRound.state.isInstanceOf[RiskTypeState] should be(true)
            }
        }
        "its toString() method its called" should {
            "return the current state information" in {
               val state = new StartState(round)
               state.execute()
               state.toString should be("\nThe Round started.\nGood Luck.\n") 
            }
        }
    }

    "A RiskTypeState" when {
        "its created" should {
            val round = new Round(player, deck)
            val state = new RiskTypeState(round)
            "have a round" in {
                state.round should be(round)
            }
        }
        "its executed " should {
            val round = new Round(player, deck)
            val state = new RiskTypeState(round)
            val newRound : Option[Round] = state.
            execute[Round, String](round.setRiskType,"low").
            asInstanceOf[Option[Round]]
            "return a round" in {
                newRound should not be(None)
            }
            "return a round with a Bet state" in {
                newRound.get.state.isInstanceOf[BetState] should be(true)
            }
            "the toString method should return current state information" in {
                state.toString should be("\nYou choose: " + newRound.get.riskType.get.message)
            }
        }
        "its executed with the argument 'low'" should {
            val round = new Round(player, deck)
            val state = new RiskTypeState(round)
            val newRound : Option[Round] = state.execute[Round, String](round.setRiskType,"low").
            asInstanceOf[Option[Round]]
            "set the riskType of the round to low" in {
                newRound.get.riskType.get should be(RiskType("low"))
            }
        }
        "its executed with the argument 'high'" should {
            val round = new Round(player, deck)
            val state = new RiskTypeState(round)
            val newRound : Option[Round] = state.
            execute[Round, String](round.setRiskType,"high").
            asInstanceOf[Option[Round]]
            "set the riskType of the round to high" in {
                newRound.get.riskType.get should be(RiskType("high"))
            }
        }
        "its executed with the argument 'high' and not enough money" should {
            val round = new Round(new Player(5), deck)
            val state = new RiskTypeState(round)
            "throw an IllegalArgumentException" in {
                an [IllegalArgumentException] should be thrownBy 
                state.execute[Round, String](round.setRiskType,"high")        
            }
            "set the Round to RiskTypeState again" in {
                round.state.isInstanceOf[RiskTypeState] should be (true)
            }
            "print the error message by calling toString()" in {
                state.toString should be("\nYou dont have enough credit to play High Risk.")
            }
        }
    }

    "A BetState" when {
        "its created" should {
            val round = new Round(player, deck)
            val state = new BetState(round)
            "have a round" in {
                state.round should be(round)
            }
        }
        "its executed" should {
            "return a round" in {
                val round = new Round(player, deck)
                round.setRiskType("low")
                val state = new BetState(round)
                val newRound : Option[Round] = state.execute(round.setBet, 20).
                asInstanceOf[Option[Round]]
                newRound should not be(None)
            }
            "return a round with a DealCardsState" in {
                val round = new Round(player, deck)
                round.setRiskType("low")
                val state = new BetState(round)
                val newRound : Round = state.execute(round.setBet, 20).get.asInstanceOf[Round]
                newRound.state.isInstanceOf[DealCardsState] should be(true)
            }
            "its toString() method return the current state information" in {
                val round = new Round(player, deck)
                round.setRiskType("low")
                val state = new BetState(round)
                val newRound : Round = state.execute(round.setBet, 20).get.asInstanceOf[Round]
                state.toString should be("\nYour bet : " + newRound.bet.get + " $\n") 
            }
        }
        "its executed with a to high bet " should {
            val round = new Round(new Player(5), deck)
            round.setRiskType("low")
            val state = new BetState(round)
            "throw an IllegalArgumentException" in {
                an [IllegalArgumentException] should be thrownBy state.execute(round.setBet, 20)
            }
            "set the Round to RiskTypeState again" in {
                round.state.isInstanceOf[BetState] should be (true)
            }
            "its toString() method return the current state information" in {
                state.toString should be("\nYou dont have enough credit.\nPlease reduce your bet.") 
            } 
        }
    }

    "A DealCardsState" when {
        "its created" should {
            val round = new Round(player, deck)
            val state = new DealCardsState(round)
            "have a round" in {
                state.round should be(round)
            }
        }
        "its executed" should {
            val round = new Round(player, deck)
            val state = new DealCardsState(round)
            "return a round" in {
                val newRound : Option[Round] = state.execute(round.dealCards()).asInstanceOf[Option[Round]]
                newRound should not be(None)
            }
            "return a round with a RiskType state" in {
                val round = new Round(player, deck)
                val state = new DealCardsState(round)
                val newRound : Round = state.execute(round.dealCards()).get.asInstanceOf[Round]
                newRound.state.isInstanceOf[HoldCardsState] should be(true)
            }
        }
        "its toString() method its called" should {
            val round = new Round(player, deck)
            val state = new DealCardsState(round)
            val newRound : Round = state.execute(round.dealCards()).get.asInstanceOf[Round]
            "return the current state information" in {
               state.toString should be("\n\n" + (1 to 5).map("["+ _.toString + "]\t\t").mkString + 
                            "\n" + newRound.hand.get.map(_.toString + "\t").mkString + "\n\n") 
            }
        }
    }
     "A HoldCardsState" when {
        "its created" should {
            val round = new Round(player, deck)
            val state = new HoldCardsState(round)
            "have a round" in {
                state.round should be(round)
            }
        }
        "its executed" should {
            val round = new Round(player, deck)
            round.dealCards()
            val state = new HoldCardsState(round)
            val vector: Vector[Int] = Vector()
            "return a round" in {
                val newRound : Option[Round] = state.execute(round.holdCards, vector).asInstanceOf[Option[Round]]
                newRound should not be(None)
            }
            "return a round with a EndState state" in {
                val round = new Round(player, deck)
                round.dealCards()
                val state = new HoldCardsState(round)
                val newRound : Round = state.execute(round.holdCards, vector).get.asInstanceOf[Round]
                newRound.state.isInstanceOf[EndState] should be(true)
            }
        }
        "its toString() method its called" should {
            val round = new Round(player, deck)
            round.dealCards()
            val state = new HoldCardsState(round)
            val vector: Vector[Int] = Vector()
            val newRound : Round = state.execute(round.holdCards, vector).get.asInstanceOf[Round]
            "return the current state information" in {
               state.toString should be("\n\n" + newRound.hand.get.map(_.toString + "\t").mkString + "\n\n") 
            }
        }
    }
}


