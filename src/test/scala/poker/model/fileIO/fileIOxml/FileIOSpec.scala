package poker
package model
package fileIO
package fileIOxml

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

class FileIOSpec extends AnyWordSpec with Matchers {
    
    "The method playerToXml()" when {
        val player = new Player(100)
        val fileIO = new FileIO()
        "its called with player as an argument" should {
            "return a suitable xml element" in {
                val xml = <player><money>{100}</money></player>
                fileIO.playerToXml(player) should be(xml)
            }
        }
    }

    "The method cardToXml()" when {
        val card = Card(Symbol.HEART, Picture.ACE, 14)
        val fileIO = new FileIO()
        "its called with player as an argument" should {
            "return a suitable xml element" in {
                val xml = <card>
            <symbol>{card.symbol}</symbol>
            <picture>{card.picture.value}</picture>
            <value>{card.value}</value>
        </card>
                fileIO.cardToXml(card) should be(xml)
            }
        }
    }

    "The method load()" when {
        val fileIO = new FileIO()
        val xml = <round>
    <bet>50</bet>
    <hand>
        <card>
            <symbol>Club</symbol>
            <picture>EIGHT</picture>
            <value>8</value>
        </card>
        <card>
            <symbol>Diamond</symbol>
            <picture>EIGHT</picture>
            <value>8</value>
        </card>
        <card>
            <symbol>Spade</symbol>
            <picture>NINE</picture>
            <value>9</value>
        </card>
        <card>
            <symbol>Diamond</symbol>
            <picture>SIX</picture>
            <value>6</value>
        </card>
        <card>
            <symbol>Diamond</symbol>
            <picture>TEN</picture>
            <value>10</value>
        </card>
    </hand>
    <riskType>high</riskType>
    <combinationHand>
        <card>
            <symbol>Club</symbol>
            <picture>EIGHT</picture>
            <value>8</value>
        </card>
        <card>
            <symbol>Diamond</symbol>
            <picture>EIGHT</picture>
            <value>8</value>
        </card>
    </combinationHand>
    <combination>PAIR</combination>
    <outcome>50</outcome>
    <player>
        <money>569</money>
    </player>
</round>
        "its called on the file" should {
            "return a suitable round" in {
                val bet = (xml \ "bet").text.toInt
                println(bet)
                val hand = (xml \ "hand" \ "card")
                val arrayhand = new Array[CardInterface](5)
                var i = 0
                for(card <- hand) {
                    val symbol = (card \ "symbol").text.toUpperCase
                    val picture = (card \ "picture").text.toUpperCase
                    val value = (card \ "value").text.toInt
                    arrayhand(i) = Card(Symbol.valueOf(symbol) , Picture.valueOf(picture), value)
                    i += 1
                }
                val riskType = (xml \ "riskType").text
                val combHand = (xml \ "combinationHand" \ "card")
                val combHandArray = new Array[CardInterface](combHand.size)
                var j = 0
                for(card <- combHand) {
                    val symbol = (card \ "symbol").text.toUpperCase
                    val picture = (card \ "picture").text.toUpperCase
                    val value = (card \ "value").text.toInt
                    combHandArray(j) = Card(Symbol.valueOf(symbol) , Picture.valueOf(picture), value)
                    j += 1
                }
                val comb = (xml \ "combination").text
                val combination = Combination.valueOf(comb)
                println(comb)
                val outcome = (xml \ "outcome").text
                val outcomee = outcome.toInt
                val playerMoney = (xml \ "player" \ "money").text
                println(playerMoney)
                println(Picture.valueOf("TEN").value)
                val money = playerMoney.toInt
            }
        }
    }
}
