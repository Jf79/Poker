package poker
package model
package fileIO
package fileIOjson

import play.api.libs.json._
import round.roundBaseImpl.Round
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import player.playerBaseImpl.Player
import poker.model.card.cardBaseImpl.Card
import poker.util.Picture
import poker.util.Symbol
import poker.util.Combination
import poker.model.card.CardInterface
import poker.model.round.roundBaseImpl.RiskType
import poker.model.round.RoundInterface
import scala.io.Source

class FileIOSpec extends AnyWordSpec with Matchers {
    "The method playerToJson()" when {
        val player = new Player(100)
        val fileIO = new FileIO()
        "its called with player as an argument" should {
            "return a suitable json object" in {
                println(Json.prettyPrint(fileIO.playerToJson(player)))
            }
        }
    }

    "The method cardToJson()" when {
        val card = Card(Symbol.HEART, Picture.ACE, 14)
        val fileIO = new FileIO()
        "its called with card as an argument" should {
            "return a suitable json object" in {
                println(Json.prettyPrint(fileIO.cardToJson(card)))
            }
        }
    }

    "The method roundToJson()" when {
        val hand = Array(Card(Symbol.HEART, Picture.ACE, 14),Card(Symbol.DIAMOND, Picture.ACE, 14))
        val fileIO = new poker.model.fileIO.fileIOxml.FileIO()
        val fileIOjson = new FileIO()
        "its called with card as an argument" should {
            "return a suitable json object" in {
                val round = fileIO.load
                println(Json.prettyPrint(fileIOjson.roundToJson(round)))
            }
        }
    }
}
