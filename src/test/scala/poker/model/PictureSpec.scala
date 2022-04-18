package poker.model

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import model._

class PictureSpec extends AnyWordSpec with Matchers { 
  "A Picture" when {
    "its printed" should {
      val picture = Picture.ACE
      "print A" in {
        picture.toString should be("A")
      }
    }
  }
}

