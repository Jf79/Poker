// For more information on writing tests, see
// https://scalameta.org/munit/docs/getting-started.html
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import poker.model
org.scalajs

class CardSpec extends WordSpec with Matchers { 
"A Card" when {
   "when its created" should {
     val card = Card(Symbol.HEART, Picture.ACE)
     "have symbol HEART" in {
       card.symbol should be(Symbol.HEART)
     }
     "have pictrue ACE" in {
       card.pictrue should be(Picture.ACE)
     }
   }

