package poker
package model
package fileIO
package fileIOjson

import java.io._

import util.Symbol
import util.Picture
import util.Combination
import util.CardsObject._
import round.RoundInterface
import card.CardInterface
import player.PlayerInterface
import poker.model.card.cardBaseImpl.Card
import scala.io.Source
import play.api.libs.json._
import poker.model.round.roundBaseImpl.RiskType
import poker.model.round.CreateRound
import poker.model.player.CreatePlayer

class FileIO extends FileIOInterface:
    override def save(round: RoundInterface, filename: String): Unit = 
        val pw = new PrintWriter(new File(filename + ".json"))
        pw.write(Json.prettyPrint(roundToJson(round)))
        pw.close
    override def load: RoundInterface = 
        var round: RoundInterface = null
        val source: String = Source.fromFile("round.json").getLines.mkString
        val json: JsValue = Json.parse(source)
        val bet = (json \ "Round" \ "Bet").get.toString.toInt
        val hand = (json \ "Round" \ "Hand").get
        val playerHand = new Array[CardInterface](5)
        for(i <- 0 to 4) 
            val symbol = (hand(i)\ "Card" \ "Symbol").get.toString.toUpperCase.replaceAll("\"","")
            val picture = (hand(i)\ "Card" \ "Picture").get.toString.toUpperCase.replaceAll("\"","")
            val value = (hand(i)\ "Card" \ "Value").get.toString.toInt
            playerHand(i) = Card(Symbol.valueOf(symbol), Picture.valueOf(picture), value)
        val outcome = (json \"Round" \ "Outcome").get.toString.toInt
        val money = (json \ "Round" \ "Player" \ "Money").get.toString.toInt
        val riskType = RiskType((json \ "Round" \ "RiskType").get.toString, money)
        val combLength = (json \ "Round" \ "CombinationCards").get.toString.toInt
        val combHand = (json \ "Round" \ "CombinationHand").get
        val playerCombHand = new Array[CardInterface](combLength)
        for(i <- 0 to combLength - 1) {
            val symbol = (combHand(i)\ "Card" \ "Symbol").get.toString.toUpperCase.replaceAll("\"","")
            val picture = (combHand(i)\ "Card" \ "Picture").get.toString.toUpperCase.replaceAll("\"","")
            val value = (combHand(i)\ "Card" \ "Value").get.toString.toInt
            playerCombHand(i) = Card(Symbol.valueOf(symbol), Picture.valueOf(picture), value)
        }
        round = CreateRound(CreatePlayer(money), createCards())
        round.bet = Some(bet)
        round.hand = Some(playerHand)
        round.combination = Some(Combination.valueOf((json \"Round" \"Combination").get.toString.toUpperCase.replaceAll("\"","")))
        round.outcome = outcome
        round.riskType = Some(RiskType((json \ "Round" \ "RiskType").get.toString, money))
        round.combinationHand = Some(playerCombHand)
        round

    def roundToJson(round: RoundInterface): JsObject = 
        var comb: String = ""
        var combHand: JsValue = Json.obj()
        var combSize: Int = 0 
        var hand: JsValue = null
        hand = Json.toJson(
                for{
                    card <- round.hand.get
                } 
                yield {
                    Json.toJson(cardToJson(card))
                }
            )
        if(round.combinationHand.isDefined) 
            combHand = Json.toJson(
                for{
                    card <- round.combinationHand.get
                } 
                yield {
                    Json.toJson(cardToJson(card))
                }
            )
            combSize = round.combinationHand.get.length
        if(round.combination.isDefined)
            comb = round.combination.get.toString()
        Json.obj(
            "Round" -> Json.obj(
                "Bet" -> JsNumber(round.bet.get),
                "Hand" -> hand,
                "RiskType" -> JsString(round.riskType.get.toString),
                "CombinationCards" -> JsNumber(combSize),
                "CombinationHand" -> combHand,
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



