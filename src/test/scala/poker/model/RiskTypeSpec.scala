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
                val t = Try{RiskType("high", 10)}
                t.isFailure should be(true)
            }
        }
        "you create it, with a player who have enough credit" should {
            val player = new Player(200)
            "not throw an Exception" in {
                val t = Try{RiskType("high", 200)}
                t.isFailure should be(false)
            }
        }
        "you call the setBet() method, with not enough credit it" should {
            val riskType = RiskType("high", 40)
            "throw an Exception" in {
                val t = Try{riskType.setBet(30, 10)}
                t.isFailure should be (true)
            }
        }
        "you call the setBet() method, with not enough credit it" should {
            val riskType = RiskType("high", 40)
            "not throw an Exception" in {
                val t = Try{riskType.setBet(10, 10)}
                t.isSuccess should be (true)
            }
        }
    }
    "Low Risk Type" when {
       "you call the setBet() method, with enough credit it" should {
            val riskType = RiskType("low", 40)
            "not throw an Exception" in {
                val t = Try{riskType.setBet(10, 10)}
                t.isSuccess should be (true)
            }
        }
        "you call the setBet() method, with not enough credit it" should {
            val riskType = RiskType("low", 40)
            "throw an Exception" in {
                val t = Try{riskType.setBet(11, 10)}
                t.isFailure should be (true)
            }
        }
    }
}