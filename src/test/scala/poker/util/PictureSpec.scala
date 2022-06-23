package poker
package model

import util.Symbol
import util.Picture

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

//import model._

class PictureSpec extends AnyWordSpec with Matchers { 
  "Picture.ACE" when {
    "its printed" should {
      val picture = Picture.ACE
      "print A" in {
        picture.toString should be("A")
      }
    }
  }
}

