package poker
package aview
package gui

import java.lang.Thread._

import java.awt.Font
import java.awt.Dimension
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Stroke
import java.awt.BasicStroke
import java.awt.Color._

import scala.swing.FlowPanel
import scala.swing.Frame

import util.GameEvent
import util.Observer
import util.Symbol._
import util.CardsObject._
import gui.Prepare._

import controller.ControllerInterface
import scala.swing.Button
import scala.swing.BorderPanel
import scala.swing.event.MouseClicked
import java.awt.Rectangle
import java.awt.Point
import java.awt.Cursor
import scala.swing.Component
import scala.swing.event.MouseMoved
import scala.swing.event.MousePressed
import scala.collection.mutable.HashMap
import poker.model.card.CardInterface


class GUI(controller: ControllerInterface) extends Frame with UserInterface:
    controller.add(this)

    val WIDTH = 1400
    val HEIGHT = 800
    val defaultCursor = new Cursor(Cursor.DEFAULT_CURSOR)
    val handCursor = new Cursor(Cursor.HAND_CURSOR)
    val backgroundColor = new Color(36, 109, 18) 
    val cardFont = new Font("TimesRoman", 0, 200)
    val textFont = new Font(Font.SERIF, Font.BOLD, 120)
    val displayStroke = new BasicStroke(5)
    val buttonMap = new HashMap[String, MyButton]

    var cardsPainted: Option[Boolean] = None
    var numberOfCardsPainted: Option[Int] = None
    var cardRects: Option[Array[CardRect]] = None
    var combBoard: Option[CombinationBoard] = None
    var messageBoard: Option[MessageBoard] = None
    var gameState: Option[GameEvent] = None

    title = "Poker"
    visible = true 
    var counter = 0
    
    size = new Dimension(WIDTH, HEIGHT)

    def run: Unit =
        gameState = Some(GameEvent.INTRO)
        setPanel

    override def update(event: GameEvent) = 
        gameState = Some(event)
        repaint()

    private def prepare: Unit = 
        prepareIntroButton(WIDTH, HEIGHT, buttonMap)
        combBoard = Some(prepareCombBoard(WIDTH, HEIGHT))
        prepareFirstButtons(WIDTH, HEIGHT, combBoard.get, buttonMap)
        prepareDealButton(WIDTH, HEIGHT, combBoard.get, buttonMap)
        cardRects = Some(prepareCards(WIDTH, HEIGHT))
        messageBoard = Some(prepareMessageBoard(WIDTH, HEIGHT, combBoard.get))
        cardsPainted = Some(false)
        numberOfCardsPainted = Some(1)

    private def paintBackground(g: Graphics2D): Unit =
        g.setColor(backgroundColor.darker)
        g.fillRect(0, 0, WIDTH, HEIGHT)
    
    def setPanel: Unit = 
        contents = new FlowPanel {
            prepare
            listenTo(mouse.clicks)
            listenTo(mouse.moves)
            reactions += {
                case click: MouseClicked => processMouseClick(click)
                case move: MouseMoved => processMouseMoved(move)
            }
            preferredSize = new Dimension(WIDTH, HEIGHT)
            override def paintComponent(g: Graphics2D): Unit = 
                paintBackground(g)
                gameState.get match {
                    case GameEvent.INTRO => introState(g)
                    case GameEvent.START => startState(g)
                    case GameEvent.PLAY => roundState(g)
                    case GameEvent.EXIT => dispose()
                    case null =>
                }
        }
    
    private def roundState(g: Graphics2D): Unit = 
        combBoard.get.repaint(g, None, controller)
        messageBoard.get.repaint(g, null)
        val roundState = controller.getStateOfRound().toString
        roundState match {
            case "Risk" => chooseRiskType(g)
            case "Bet" => setBet(g)
            case "Deal" => setBet(g)
            case "Hold" => holdCards(g)
            case "Evaluation" => evaluation(g)
            case "End" => end(g)
        }
    
    private def holdCards(g: Graphics2D) = 
        messageBoard.get.repaint(g, handleFailure("  Which cards\n         you\n  wanna hold ?"))
        buttonMap.get("BackButton").get.setVisible(false)
        buttonMap.get("CoinButton").get.setVisible(false)
        buttonMap.get("DealButton").get.setVisible(true)
        .asInstanceOf[DealButton].setCardRects(this).setCoinButton(null).setHoldState(true).repaint(g)
        paintCards(g)
    
    private def evaluation(g: Graphics2D) = 
        paintOldCards(g)
        checkCombination
        paintOldCards(g)
    
    private def end(g: Graphics2D) =
        buttonMap.get("DealButton").get.setVisible(false)
        paintOldCards(g)
        paintCards(g)
        refreshCardRects
    
    private def drawResult(): Option[String] =
        val comb = controller.round.get.combination
        val outcome = controller.round.get.getOutcome()
        if(comb.get.getRank < 10)
            return Some("Congratulations\n You won: " + outcome + " $")
        Some("      No Win\nBut Good Luck\n     Next time")

    
    private def checkCombination = 
        controller.doAndPublish(controller.evaluation())
        new Thread(() => {
            sleep(800)
            val comb = controller.round.get.combination
            messageBoard.get.setCombination(drawResult())
            setHandOfCombination()
            combBoard.get.setCombination(comb)        
            if(comb.get.getRank < 10)
                for(i <- 0 until 15)
                    if(i % 2 == 0)
                        combBoard.get.setCombination(comb)
                    else
                        combBoard.get.setCombination(None)
                    sleep(400)
            else
                sleep(3000)
            controller.startTheGame()
            prepare
        }).start()
    
    private def setHandOfCombination() =
        val handOfComb = controller.round.get.getCombinationHand()
        val hand = controller.round.get.getHandOfPlayer()
        println(handOfComb.isDefined)
        if(handOfComb.isDefined)
            println(handOfComb.get)
        if(handOfComb.isDefined)
            handOfComb.get.foreach(card =>
                val index = hand.indexOf(card)
                if(index != -1)
                    cardRects.get(index).setPartOfCombination(true)
            )
        

    private def paintOldCards(g: Graphics2D) : Unit = 
        val hand = controller.getHandOfPlayer()
        for(j <- 0 until 5)
            if(cardRects.get(j).isHolded)
                cardRects.get(j).repaint(g)
        repaint()

    private def paintCards(g: Graphics2D) : Unit = 
        val hand = controller.getHandOfPlayer()
        val size = hand.length
        for(j <- 0 until numberOfCardsPainted.get)
            if(!cardsPainted.get && !cardRects.get(j).isHolded)
                val symbol = hand(j).symbol
                val picture = hand(j).picture
                cardRects.get(j).setCard(symbol, picture).setClickable(true).setVisible(true).repaint(g)
                sleep(35)
            if(!cardRects.get(j).isHolded)
                cardRects.get(j).repaint(g)
            repaint()
        checkCardsPainted
    
    private def checkCardsPainted: Unit =
        if(numberOfCardsPainted.get < 5) 
            numberOfCardsPainted = Some(numberOfCardsPainted.get + 1)
        else
            cardsPainted = Some(true)
    
    def refreshCardRects = 
        cardRects.get.foreach(card =>
            card.isClicked = false
            card.borderColor = card.color
            card.stroke = card.normalStroke
            card.setClickable(false)
            card.backgroundColor = white
        )

    private def setBet(g: Graphics2D) = 
        drawPlayerCredit(g)
        drawCoin(g)
        messageBoard.get.repaint(g, handleFailure("  Please place\n     your bet"))
        buttonMap.get("LowButton").get.setVisible(false)
        buttonMap.get("HighButton").get.setVisible(false)
        buttonMap.get("BackButton").get.setVisible(true).repaint(g)
        val coin = buttonMap.get("CoinButton").get.setVisible(true).asInstanceOf[CoinButton]
        coin.repaint(g)
        buttonMap.get("DealButton").get.setVisible(true).asInstanceOf[DealButton]
        .setCoinButton(coin).setHoldState(false).repaint(g)
        combBoard.get.repaint(g, Some(coin.getClick), controller)
    
    private def chooseRiskType(g: Graphics2D) = 
        drawPlayerCredit(g)
        messageBoard.get.repaint(g, handleFailure(" Which type of\n    game you\n want to play?"))
        buttonMap.get("BackButton").get.setVisible(false)
        buttonMap.get("CoinButton").get.setVisible(false)
        buttonMap.get("DealButton").get.setVisible(false)
        buttonMap.get("ExitButton").get.setVisible(false)
        buttonMap.get("StartButton").get.setVisible(false)
        buttonMap.get("LowButton").get.setVisible(true).repaint(g)
        buttonMap.get("HighButton").get.setVisible(true).repaint(g)
    
    private def startState(g: Graphics2D): Unit = 
        drawPlayerCredit(g)
        if(!controller.hasEnoughCredit())
            notEnoughCredit(g)
            return
        messageBoard.get.repaint(g, "  Do you want\n  to continue ?")
        combBoard.get.repaint(g, None, controller)
        buttonMap.get("IntroButton").get.setVisible(false)
        buttonMap.get("ExitButton").get.setVisible(true).repaint(g)
        buttonMap.get("StartButton").get.setVisible(true).repaint(g)
    
    private def introState(g: Graphics2D): Unit = 
        g.setColor(BLACK)
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 120))
        g.drawString("Welcome to Poker", 160, 300)
        buttonMap.get("IntroButton").get.setVisible(true).repaint(g)
    
    private def notEnoughCredit(g: Graphics2D): Unit = 
        messageBoard.get.repaint(g, "You dont have\n    enough\ncredit to play")
        combBoard.get.repaint(g, None, controller)
        buttonMap.get("IntroButton").get.setVisible(false)
        buttonMap.get("ExitButton").get.setVisible(true).asInstanceOf[ExitButton]
        .setText("EXIT").repaint(g)

    private def drawPlayerCredit(g: Graphics2D) =
        g.setColor(BLACK)
        g.setFont(new Font("Arial", Font.BOLD, 42))
        val credit = controller.getCreditOfPlayer()
        g.drawString("CREDIT:    ", messageBoard.get.bottomL.x + 10, messageBoard.get.bottomL.y + 55)
        g.drawString(credit.toString, messageBoard.get.bottomL.x + 260, messageBoard.get.bottomL.y + 55)

    private def drawCoin(g: Graphics2D) =
        g.setColor(BLACK)
        g.setFont(new Font("Arial", Font.BOLD, 42))
        val credit = controller.getCreditOfPlayer()
        g.drawString("COIN:    ", messageBoard.get.bottomL.x + 60, messageBoard.get.bottomL.y + 100)
        g.drawString(controller.round.get.riskType.get.getMinimumBet.toString, messageBoard.get.bottomL.x + 260, messageBoard.get.bottomL.y + 100)

    private def drawBet(g: Graphics2D) =
        g.setColor(BLACK)
        g.setFont(new Font("Arial", Font.BOLD, 42))
        val bet = controller.round.get.getBet().toString
        g.drawString("BET:    ", messageBoard.get.bottomL.x + 10, messageBoard.get.bottomL.y + 55)
        g.drawString(bet, messageBoard.get.bottomL.x + 260, messageBoard.get.bottomL.y + 55)

 
    
    private def handleFailure(message: String): String =
        if(!controller.round.get.failed) return message
        controller.round.get.updateMessage
    
    private def processMouseMoved(event : MouseMoved): Unit = 
        val point = event.point
        if(enteredButton(point) || enteredCards(point))
            cursor = handCursor
        else 
            cursor = defaultCursor
        repaint()


    private def processMouseClick(e : MouseClicked): Unit =
        if(e.point.x < 40 && e.point.y < 40)
            dispose()
        if(clickedButtons(e.point) || clickedCards(e.point))
            repaint()
    
    private def clickedButtons(point: Point): Boolean =
        val buttonKeys = buttonMap.keySet
        var clicked = false
        buttonKeys.foreach(c =>
            if(buttonMap.get(c).get.visible)
                if(buttonMap.get(c).get.isEntered(point))
                    buttonMap.get(c).get.clicked(controller)
                    clicked = true
            )
        clicked
    
    private def clickedCards(point: Point): Boolean =
        for(i <- 0 to 4)
            if(cardRects.get(i).visible && cardRects.get(i).isEntered(point))
                cardRects.get(i).clicked()
                return true
        false
    
    private def enteredButton(point: Point): Boolean = 
        val buttonKeys = buttonMap.keySet
        var entered = false
        buttonKeys.foreach(c =>
            if(buttonMap.get(c).get.visible)
                if(buttonMap.get(c).get.isEntered(point))
                    buttonMap.get(c).get.enter()
                    entered = true
                else
                    buttonMap.get(c).get.leaved()
            )
        entered
        
    private def enteredCards(point: Point): Boolean =
        var entered = false
        for(i <- 0 to 4)
            if(cardRects.get(i).visible && cardRects.get(i).clickAble && cardRects.get(i).isEntered(point))
                cardRects.get(i).enter()
                entered = true
            else
                cardRects.get(i).leaved()
        entered
