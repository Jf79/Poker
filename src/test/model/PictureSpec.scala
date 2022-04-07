import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import poker.model

class PictureSpec extends WordSpec with Matchers { 
  "A Picture" when {
    "its printed" should {
      val picture = Picture.ACE
      "print A" in {
        picutre.toString should be("A")
      }
    }
  }
}

