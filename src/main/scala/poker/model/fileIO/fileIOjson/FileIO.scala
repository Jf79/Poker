package poker
package model
package fileIO
package fileIOjson

import java.io._

import util.Symbol
import util.Picture
import util.Combination
import round.RoundInterface
import play.api.libs.json._
import card.CardInterface
import player.PlayerInterface
import poker.model.card.cardBaseImpl.Card
import scala.io.Source

class FileIO extends FileIOInterface:
    override def save(round: RoundInterface, filename: String): Unit = 
        val pw = new PrintWriter(new File(filename + ".json"))
        pw.write(Json.prettyPrint(roundToJson(round)))
        pw.close
    override def load: RoundInterface = 
        var round: RoundInterface = null
        val source: String = Source.fromFile("round.json").getLines.mkString
        val json: JsValue = Json.parse(source)
        round

    def roundToJson(round: RoundInterface): JsObject = 
        var comb: String = null
        var combHand: JsValue = null
        if(round.combinationHand.isDefined) 
            combHand = Json.toJson(
                for{
                    card <- round.combinationHand.get
                } 
                yield {
                    Json.toJson(cardToJson(card))
                }
            )
        if(round.combination.isDefined)
            comb = round.combination.get.toString()
        Json.obj(
            "Round" -> Json.obj(
                "Bet" -> JsNumber(round.bet.get),
                "Hand" -> combHand,
                "RiskType" -> JsString(round.riskType.get.toString),
                "CombinationHand" -> Json.toJson(
                    for{
                        card <- round.combinationHand.get
                    } 
                    yield {
                        Json.toJson(cardToJson(card))
                    }
                ),
                "Combination" -> comb,
                "Outcome" -> JsNumber(round.getOutcome()),
                "Player" -> playerToJson(round.getPlayer)
            )
        )

    def cardToJson(card: CardInterface): JsObject = 
        Json.obj(
            "Card" -> Json.obj(
                "Symbol" -> card.symbol.toString,
                "Picture" -> card.picture.value,
                "Value" -> card.value,
            )
        )
    
    def playerToJson(player: PlayerInterface) : JsObject =
        Json.obj(
                "Money" -> JsNumber(player.getMoney())
        )



