package poker
package model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import CardsObject._
import util.Symbol
import util.Picture

class RoundSpec extends AnyWordSpec with Matchers {
  val player = new Player(1000)
  val bet = 100
  val deck = createCards()
  var round = new Round(player, deck)

  "A round" when {
    "its created" should {
      "have a player with money" in {
        round.player.money should be(1000)
      }
      "have deck of cards" in {
        round.deck should not be(null)
      }
      "be in the StartState" in {
        round.state.isInstanceOf[StartState] should be(true)
      }
    }
  }
  "The method setRiskType" when {
    "its called with 'high'" should {
      val round = new Round(new Player(300), createCards())
      val newRound = round.setRiskType("high").get
      "choose the HighRisk strategy" in {
        newRound.riskType.get should be(new HighRisk(30))
      }
    }
    "its called with 'low'" should {
      val round = new Round(new Player(300), createCards())
      val newRound = round.setRiskType("low").get
      "choose the LowRisk strategy" in {
        newRound.riskType.get should be(new LowRisk())
      }
    }
  }
  "The method setBet" when {
    val bet = 5
    "its called at LowRisk" should {
      val round = new Round(new Player(300), createCards())
      val newRound = round.setRiskType("low").get
      newRound.setBet(bet)
      "set bet to the value of argument" in {
        newRound.bet.get should be(bet)
      }
    }
    "its called at HighRisk" should {
      val round = new Round(new Player(300), createCards())
      val newRound = round.setRiskType("high").get
      newRound.setBet(bet)
      "set bet to the value of argument, but at least 30 $" in {
        newRound.bet.get should be(30)
      }
    }
  }

  "The method dealCards" when {
    "its called" should {
      val newRound = round.dealCards()
      "set 5 cards to hand" in {
        newRound.hand.get.length should be(5)
      }
      "only 47 cards remain in the deck" in {
        newRound.deck.length should be(47)
      }
    }
  }

  "The method holdCards" when {
    "its called with an empty vector" should {
      val round = new Round(new Player(300), createCards())
      round.dealCards()
      val oldHand = round.hand.get.clone
      "replace all cards" in {
        val newRound = round.holdCards(Vector()).get
        newRound.hand.get should not be(oldHand)
      }
    }
    "its called with an full vector of five numbers" should {
      val round = new Round(new Player(300), createCards())
      round.dealCards()
      val oldHand = round.hand.get.clone
      "hold all cards" in {
        val newRound = round.holdCards(Vector(1, 2, 3, 4, 5)).get
        newRound.hand.get should be(oldHand)
      }
    }
  }
  "The method replaceCards" when {
    val round = new Round(new Player(300), createCards())
    val cards = Array(
      new Card(Symbol.HEART, Picture.ACE, 14),
      new Card(Symbol.HEART, Picture.KING, 13),
      new Card(Symbol.HEART, Picture.QUEEN, 12),
      new Card(Symbol.HEART, Picture.JACK, 11),
      new Card(Symbol.HEART, Picture.TEN, 10))
      val nCards = Array(
      new Card(Symbol.DIAMOND, Picture.ACE, 14),
      new Card(Symbol.DIAMOND, Picture.KING, 13),
      new Card(Symbol.DIAMOND, Picture.QUEEN, 12),
      new Card(Symbol.DIAMOND, Picture.JACK, 11),
      new Card(Symbol.DIAMOND, Picture.TEN, 10))
    "its called with an empty vector" should {
      "replace all cards" in {
        val nHand = round.replaceCards(Vector(), nCards, cards)
        nHand should be (nCards)
      }
    }
    "its called with an full vector" should {
      "not replace a card" in {
        val nHand = round.replaceCards(Vector(1, 2, 3, 4, 5), nCards, cards)
        nHand should be (cards)
      }
    }
  }

  "The method hasEnoughCredit" when {
    "the player have any money" should {
      val round = new Round(new Player(300), createCards())
      "return true" in {
        round.hasEnoughCredit() should be(true)
      }
    }
    "the player dont have any money" should {
      val round = new Round(new Player(0), createCards())
      "return false" in {
        round.hasEnoughCredit() should be(false)
      }
    }
  }

  "The method handle " when {
    val round = new Round(new Player(300), createCards())
    "its called with an RiskTypeEvent" should {
      "return a RiskTypeState" in {
        round.handle(new RiskTypeEvent) should be(new RiskTypeState(round))
      }
    }
    "its called with a BetEvent" should {
      "return a BetState" in {
        round.handle(new BetEvent) should be(new BetState(round))
      }
    }
    "its called with a DealCardsEvent" should {
      "return a BetState" in {
        round.handle(new DealCardsEvent) should be(new DealCardsState(round))
      }
    }
    "its called with a HoldCardsEvent" should {
      "return a BetState" in {
        round.handle(new HoldCardsEvent) should be(new HoldCardsState(round))
      }
    }
    "its called with an EndEvent" should {
      "return a BetState" in {
        round.handle(new EvaluationEvent) should be(new EvaluationState(round))
      }
    }
  }

}
