package poker
package model

import scala.util.Try
import util.CardsObject._
import org.scalatest._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import poker.model.round.roundBaseImpl.Round
import poker.model.round.roundBaseImpl.StartState
import poker.model.player.playerBaseImpl.Player
import poker.model.round.roundBaseImpl.RiskTypeState
import poker.model.round.roundBaseImpl.BetState
import poker.model.round.roundBaseImpl.RiskType
import poker.model.round.roundBaseImpl.RiskTypeEvent
import poker.model.round.roundBaseImpl.BetEvent
import poker.model.round.roundBaseImpl.DealCardsState
import poker.model.round.roundBaseImpl.HoldCardsState
import poker.model.round.roundBaseImpl.EvaluationState

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
            val newRound = state.execute[String](round.setRiskType,"low")
            "return a round" in {
                newRound should not be(None)
            }
            "return a round with a Bet state" in {
                newRound.get.state.isInstanceOf[BetState] should be(true)
            }
        }
        "its executed with the argument 'low'" should {
            val round = new Round(player, deck)
            val state = new RiskTypeState(round)
            val newRound = state.execute[String](round.setRiskType,"low")
            "set the riskType of the round to low" in {
                newRound.get.riskType.get should be (RiskType("low", 0))
            }
        }
        "its executed with the argument 'high'" should {
            val round = new Round(player, deck)
            val state = new RiskTypeState(round)
            val newRound = state.execute[String](round.setRiskType,"high")
            "set the riskType of the round to high" in {
                newRound.get.riskType.get should be(RiskType("high", 40))
            }
        }
        "its executed with the argument 'high' and not enough money" should {
            val round = new Round(new Player(5), deck)
            round.handle(new RiskTypeEvent)
            val state = new RiskTypeState(round)
            val roundd = state.execute[String](round.setRiskType,"high")
            println(roundd.get.updateMessage)
            "set the Round to RiskTypeState again" in {
                round.state.isInstanceOf[RiskTypeState] should be (true)
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
        }
        "its executed with a to high bet " should {
            val round = new Round(new Player(5), deck)
            round.handle(new BetEvent)
            round.setRiskType("low")
            val state = new BetState(round)
            val nRound = state.execute(round.setBet, 20).get
            "set the Round to RiskTypeState again" in {
                nRound.state.isInstanceOf[BetState] should be (true)
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
                val newRound = state.execute(round.holdCards, vector)
                newRound should not be(None)
            }
            "return a round with a EndState state" in {
                val round = new Round(player, deck)
                round.dealCards()
                val state = new HoldCardsState(round)
                val newRound = state.execute(round.holdCards, vector).get
                newRound.state.isInstanceOf[EvaluationState] should be(true)
            }
        }
    }
}


