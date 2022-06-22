package poker
package model

import player.playerBaseImpl.Player
import round.Round
import round.State._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import util.CardsObject._
import util.Symbol
import util.Picture
import util.Combination._
import poker.model.round.RiskType
import poker.model.round.BetEvent
import poker.model.card.CardInterface
import poker.util.Combination

class RoundSpec extends AnyWordSpec with Matchers {
  val player = new Player(1000)
  val bet = 100
  val deck = createCards()
  var round = new Round(player, deck)
  round.bet = Some(10)
  round.dealCards()
  round.riskType = Some(RiskType("high", 10))
  round.updateMessage = "hello"

  "A Round " when {
    val hand: Array[CardInterface] = Array(new Card(Symbol.DIAMOND, Picture.EIGHT, 8), new Card(Symbol.HEART, Picture.EIGHT, 8)
    ,new Card(Symbol.DIAMOND, Picture.KING, 13),new Card(Symbol.CLUB, Picture.THREE, 8)
    ,new Card(Symbol.HEART, Picture.TWO, 2))
    val left: Array[CardInterface] = Array(new Card(Symbol.CLUB, Picture.THREE, 8),new Card(Symbol.DIAMOND, Picture.KING, 13),new Card(Symbol.HEART, Picture.TWO, 2))
    "you call filterCombination() with a valid combination" should {
      "return the cards in the combination" in {
        val cards = round.filterCombination((Some(PAIR), Some(left)), Some(hand))
        cards.get.length should be(2)
        cards.get(0).picture should be(Picture.EIGHT)
        cards.get(1).picture should be(Picture.EIGHT)
      }
    }
    
    "you call checkCombination() with a valid hand" should {
      val hand: Array[CardInterface] = Array(new Card(Symbol.DIAMOND, Picture.EIGHT, 8), new Card(Symbol.HEART, Picture.EIGHT, 8)
      ,new Card(Symbol.DIAMOND, Picture.KING, 13),new Card(Symbol.CLUB, Picture.THREE, 3)
      ,new Card(Symbol.HEART, Picture.TWO, 2))
      "return the combination and the hand of the combination" in {
        val tuple = round.checkCombination(hand)
        tuple._1.get should be(Combination.PAIR)
        tuple._2.get.length should be(2)
      }
    }
  }

  /*"A Round" when {
    "you call the copy() method" should {
      val newRound = round.copyRound().asInstanceOf[Round]
      round.bet = Some(20)
      round.dealCards()
      round.riskType = Some(RiskType("high", 50))
      round.updateMessage = "helloooooooooo"
      round.player.addMoney(100)
      println(newRound.bet.get)
      newRound.hand.get.foreach(c => println(c.toString))
      round.hand.get.foreach(c => println(c.toString))
      println(newRound.riskType.get.message)
      println(newRound.updateMessage)
      println(newRound.player.getMoney())
      println(round.player.getMoney())
      round.handle(new BetEvent)
      println(round.state.toString)
      println(newRound.state.toString)
    }
  }*/

  /*"A round" when {
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
  }*/

}
