package poker
package aview

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import controller.Controller
import model.CardsObject._

class TUISpec extends AnyWordSpec with Matchers {
  "The TUI " when {
    "it process the input " should {
      val array = Array("1", "2", "3", "4", "5")
      val vector = Vector(1, 2, 3, 4, 5)
      "return a Vector of integers " in {
        val tui = new TUI(new Controller(null))
        val result = tui.processInput(array)
        result should be(vector)
      }
    }
  }
}
