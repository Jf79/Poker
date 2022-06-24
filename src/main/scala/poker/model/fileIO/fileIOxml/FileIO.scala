package poker
package model
package fileIO
package fileIOxml

import java.io.PrintWriter
import java.io.File

import scala.xml.Elem
import scala.xml.PrettyPrinter

import util.Combination
import util.Symbol
import util.CardsObject._
import util.Picture
import player.PlayerInterface
import card.CardInterface
import round.RoundInterface
import card.cardBaseImpl.Card
import poker.model.round.CreateRound
import poker.model.player.CreatePlayer
import poker.model.round.roundBaseImpl.RiskType


class FileIO extends FileIOInterface:
    
    override def save(round: RoundInterface, filename: String): Unit = 
        saveString(round, filename + ".xml")

    override def load: RoundInterface = 
        var round: RoundInterface = null
        val file = scala.xml.XML.loadFile("round.xml")
        val bet = (file \ "bet").text.toInt
        val hand = (file \ "hand" \ "card")
        val playerHand = new Array[CardInterface](5)
        var i = 0
        for(card <- hand) {
            val symbol = (card \ "symbol").text.toUpperCase
            val picture = (card \ "picture").text
            val value = (card \ "value").text.toInt
            playerHand(i) = Card(Symbol.valueOf(symbol), Picture.valueOf(picture), value)
            i += 1
        }
        val riskType = (file \ "riskType").text
        val combHand = (file \ "combinationHand" \ "card")
        val combHandArray = new Array[CardInterface](combHand.size)
        var j = 0
        for(card <- combHand) {
            val symbol = (card \ "symbol").text.toUpperCase
            val picture = (card \ "picture").text.toUpperCase
            val value = (card \ "value").text.toInt
            combHandArray(j) = Card(Symbol.valueOf(symbol) , Picture.valueOf(picture), value)
            j += 1
        }  
        val combination = Combination.valueOf((file \ "combination").text)
        val outcome = (file \ "outcome").text  
        val money = (file \ "player" \ "money").text.toInt      
        round = CreateRound(CreatePlayer(money), createCards())
        round.combination = Some(combination)
        round.hand = Some(playerHand)
        round.outcome = outcome.toInt
        round.riskType = Some(RiskType(riskType, money))
        round.bet = Some(bet)
        round.combinationHand = Some(combHandArray)
        round

    def saveString(round: RoundInterface, filename: String): Unit = 
        val pw = new PrintWriter(new File(filename))
        val prettyPrinter = new PrettyPrinter(120, 4)
        val xml = prettyPrinter.format(roundToXml(round))
        pw.write(xml)
        pw.close

    def playerToXml(player: PlayerInterface): Elem =
        <player><money>{player.getMoney()}</money></player>
    
    def cardToXml(card: CardInterface): Elem = 
        <card>
            <symbol>{card.symbol}</symbol>
            <picture>{card.picture.value}</picture>
            <value>{card.value}</value>
        </card>
    
    def roundToXml(round: RoundInterface): Elem =
        var combH: Array[Elem] = null
        if(round.combinationHand.isDefined) 
            combH = round.combinationHand.get.map(c => cardToXml(c))
        var comb: Combination = Combination.NOTHING
        if(round.combination.isDefined)
            comb = round.combination.get
        <round>
            <bet>{round.getBet()}</bet>
            <hand>{round.getHandOfPlayer().map(c => cardToXml(c))}</hand>
            <riskType>{round.riskType.get.toString}</riskType>
            <combinationHand>{combH}</combinationHand>
            <combination>{comb.toString()}</combination>
            <outcome>{round.getOutcome()}</outcome>
            {playerToXml(round.getPlayer)}
        </round>



