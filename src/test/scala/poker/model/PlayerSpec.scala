package poker
package model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import player.Player

class PlayerSpec extends AnyWordSpec with Matchers {
  "A Player" when {
    "its created with the argument 100" should {
      val player = new Player(100)
      "have 100 $" in {
        player.money should be(100)
      }
    }
  }
}
