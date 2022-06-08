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

import controller.controller.ControllerInterface
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


class GUI(controller: ControllerInterface) extends Frame with Observer:
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
    var cardRects: Array[CardRect] = null
    var combBoard: CombinationBoard = null
    var messageBoard: MessageBoard = null
    var gameState: GameEvent = null

    title = "Poker"
    visible = true
    var counter = 0
    
    size = new Dimension(WIDTH, HEIGHT)

    def run: Unit = 
        gameState = GameEvent.INTRO
        setPanel

    override def update(event: GameEvent) = 
        gameState = event
        repaint()

    private def prepare: Unit = 
        prepareIntroButton(WIDTH, HEIGHT, buttonMap)
        combBoard = prepareCombBoard(WIDTH, HEIGHT)
        prepareFirstButtons(WIDTH, HEIGHT, combBoard, buttonMap)
        prepareDealButton(WIDTH, HEIGHT, combBoard, buttonMap)
        cardRects = prepareCards(WIDTH, HEIGHT)
        messageBoard = prepareMessageBoard(WIDTH, HEIGHT, combBoard)
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
                counter += 1
                println(counter)
                paintBackground(g)
                gameState match {
                    case GameEvent.INTRO => introState(g)
                    case GameEvent.START => startState(g)
                    case GameEvent.PLAY => roundState(g)
                    case GameEvent.EXIT => dispose()
                    case null =>
                }
        }
    
    private def roundState(g: Graphics2D): Unit = 
        combBoard.repaint(g, None, None, controller)
        messageBoard.repaint(g, null)
        val roundState = controller.getStateOfRound().toString
        roundState match {
            case "Risk" => chooseRiskType(g)
            case "Bet" => setBet(g)
            case "Hold" => holdCards(g)
            case "Evaluation" => 
            case "End" => 
        }
    
    private def holdCards(g: Graphics2D) = 
        messageBoard.repaint(g, handleFailure("  Which cards\n        you\n  wanna hold ?"))
        buttonMap.get("BackButton").get.setVisible(false)
        buttonMap.get("CoinButton").get.setVisible(false)
        buttonMap.get("DealButton").get.setVisible(true)
        .asInstanceOf[DealButton].setCoinButton(null).setHoldState(true).repaint(g)
        paintCards(g)

    
    private def paintCards(g: Graphics2D) : Unit = 
        val hand = controller.getHandOfPlayer()
        val size = hand.length
        for(j <- 0 until numberOfCardsPainted.get)
            if(!cardsPainted.get)
                val symbol = hand(j).symbol
                val picture = hand(j).picture
                cardRects(j).setCard(symbol, picture).setVisible(true).repaint(g)
                sleep(30)
            cardRects(j).repaint(g)
            repaint()
        if(numberOfCardsPainted.get < 5) 
            numberOfCardsPainted = Some(numberOfCardsPainted.get + 1)
        else
            cardsPainted = Some(true)
    
    private def setBet(g: Graphics2D) = 
        messageBoard.repaint(g, handleFailure(" Please place\n    your bet"))
        buttonMap.get("LowButton").get.setVisible(false)
        buttonMap.get("HighButton").get.setVisible(false)
        buttonMap.get("BackButton").get.setVisible(true).repaint(g)
        val coin = buttonMap.get("CoinButton").get.setVisible(true).asInstanceOf[CoinButton]
        coin.repaint(g)
        println(coin.getClick)
        buttonMap.get("DealButton").get.setVisible(true).asInstanceOf[DealButton]
        .setCoinButton(coin).setHoldState(false).repaint(g)
        combBoard.repaint(g, Some(coin.getClick), None, controller)
    
    private def chooseRiskType(g: Graphics2D) = 
        messageBoard.repaint(g, handleFailure("Which type of\n   game you\nwant to play?"))
        buttonMap.get("ExitButton").get.setVisible(false)
        buttonMap.get("StartButton").get.setVisible(false)
        buttonMap.get("LowButton").get.setVisible(true).repaint(g)
        buttonMap.get("HighButton").get.setVisible(true).repaint(g)
        //cardRects.foreach(c => c.setVisible(true).repaint(g))
    
    private def startState(g: Graphics2D): Unit = 
        messageBoard.repaint(g, " Do you want\n to continue ?")
        combBoard.repaint(g, None, None, controller)
        buttonMap.get("IntroButton").get.setVisible(false)
        buttonMap.get("ExitButton").get.setVisible(true).repaint(g)
        buttonMap.get("StartButton").get.setVisible(true).repaint(g)
        //cardRects.foreach(c => c.setVisible(true).repaint(g))
    
    private def introState(g: Graphics2D): Unit = 
        g.setColor(BLACK)
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 120))
        g.drawString("Welcome to Poker", 160, 300)
        buttonMap.get("IntroButton").get.setVisible(true).repaint(g)
    
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
            if(cardRects(i).visible && cardRects(i).isEntered(point))
                cardRects(i).clicked()
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
            if(cardRects(i).visible && cardRects(i).isEntered(point))
                cardRects(i).enter()
                entered = true
            else
                cardRects(i).leaved()
        entered
