package poker
package controller
package controllerMockImpl


import util.Symbol
import util.Picture

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import poker.model.card.cardBaseImpl.Card

class ControllerSpec extends AnyWordSpec with Matchers { 

    val controller = new Controller(null)
    "The method startRound " should {
        "return null" in {
            controller.startRound(null) should be(null)
        }
    } 
    "The method chooseRiskType " should {
        "return null" in {
            controller.chooseRiskType(null) should be(null)
        }
    }
    "The method setBet " should {
        "return null" in {
            controller.setBet(0) should be(null)
        }
    } 
    "The method dealCards " should {
        "return null" in {
            controller.dealCards() should be(null)
        }
    } 
    "The method holdCards " should {
        "return null" in {
            controller.holdCards(null) should be(null)
        }
    } 
    "The method evaluation " should {
        "return null" in {
            controller.evaluation() should be(null)
        }
    } 
    "The method getStateOfRound " should {
        "return null" in {
            controller.getStateOfRound() should be(null)
        }
    } 
    "The method createDeck " should {
        "return null" in {
            controller.createDeck() should be(null)
        }
    } 
    "The method hasEnoughCredit " should {
        "return null" in {
            controller.hasEnoughCredit() should be(false)
        }
    } 
    "The method getHandOfPlayer " should {
        "return null" in {
            controller.getHandOfPlayer() should be(null)
        }
    } 
    "The method getCreditOfPlayer " should {
        "return null" in {
            controller.getCreditOfPlayer() should be(0)
        }
    } 
    "The method toString " should {
        "return null" in {
            controller.toString should be(null)
        }
    } 
}