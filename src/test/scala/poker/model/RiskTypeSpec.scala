package poker
package model

import util.RiskType

import scala.util.{Try, Success, Failure}
import org.scalatest._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class RiskTypeSpec extends AnyWordSpec with Matchers { 
    val player = new Player(10)
    "High Risk Type" when {
        "you create it, with a player who dont have enough credit" should {
            "throw an Exception " in {
                val x = Try{RiskType("high", player)}
                x.isFailure should be(true)
            }

        }
        "you create it, with a player who have enough credit" should {
            val player = new Player(200)
            "not throw an Exception" in {
                val x = Try{RiskType("high", player)}
                x.isFailure should be(false)
            }
        }
        /*"you call the setBet() method, with not enough credit" should {
            "throw an Exception" in {
                3 should be(3)
            }
        }*/
    }
    /*"Low Risk Type" when {

    }*/
}
