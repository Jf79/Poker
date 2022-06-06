package poker
package aview
package gui

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

import model.Card
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

    var cardRects: Array[CardRect] = new Array(5)
    var combBoard: CombinationBoard = null
    var introButton: IntroButton = null
    var messageBoard: MessageBoard = null
    var gameState: GameEvent = null

    title = "Poker"
    visible = true
    
    size = new Dimension(WIDTH, HEIGHT)

    def run: Unit = 
        gameState = GameEvent.INTRO
        setPanel

    override def update(event: GameEvent) = 
        gameState = event
        repaint()

    private def prepare: Unit = 
        prepareIntroButton
        prepareCombBoard
        prepareCards
        prepareMessageBoard
    
    private def prepareMessageBoard: Unit =
        val startX = combBoard.topR.x + 50
        val startY = combBoard.topR.y
        val xR = startX + (WIDTH/4).toInt // width 
        val yB = startY + (combBoard.height/2.3).toInt // height
        val topL = new Point(startX, startY)
        val topR = new Point(xR, startY)
        val bottomL = new Point(startX, yB)
        val bottomR = new Point(xR, yB)
        //buttonMap.put("MessageBoard", messageBoard)
        messageBoard = new MessageBoard(topL, topR, bottomR, bottomL, YELLOW)

    private def prepareCards: Unit =
        val startX = (WIDTH/50).toInt
        val startY = (HEIGHT/2).toInt
        val cardWidth = 190
        val cardHeight = 270
        val space = 30
        val hand = getRandomCards(controller.createDeck(), 5)._1
        for(i <- 0 until cardRects.length)
            val pointX = startX + i * (cardWidth + space)
            val topL = new Point(pointX, startY)
            val topR = new Point(pointX + cardWidth, startY)
            val bottomR = new Point(pointX + cardWidth, startY + cardHeight)
            val bottomL = new Point(pointX , startY + cardHeight)
            cardRects(i) = new CardRect(topL, topR, bottomR, bottomL, Color.BLACK)
            cardRects(i).setCard(hand(i).symbol, hand(i).picture)
            
    private def prepareCombBoard: Unit = 
        val startX = WIDTH/50
        val startY = HEIGHT/50
        val xR = startX + (WIDTH/1.7).toInt // width 
        val yB = startY + (HEIGHT/2.3).toInt // height
        val topL = new Point(startX, startY)
        val topR = new Point(xR, startY)
        val bottomL = new Point(startX, yB)
        val bottomR = new Point(xR, yB)
        combBoard = new CombinationBoard(topL, topR, bottomR, bottomL, YELLOW)

    private def prepareIntroButton: Unit = 
        val startX = (WIDTH/2.8).toInt
        val startY = (HEIGHT/2).toInt
        val xR = startX + (WIDTH/5).toInt // width 
        val yB = startY + (HEIGHT/9).toInt // height
        val topL = new Point(startX, startY)
        val topR = new Point(xR, startY)
        val bottomL = new Point(startX, yB)
        val bottomR = new Point(xR, yB)
        val introButton = new IntroButton(topL, topR, bottomR, bottomL, YELLOW, "START")
        introButton.visible = true
        buttonMap.put("IntroButton", introButton)

    private def paintBackground(g: Graphics2D): Unit =
        g.setColor(backgroundColor.darker)
        g.fillRect(0, 0, WIDTH, HEIGHT)

    private def paintIntroduction(g: Graphics2D): Unit = 
        g.setColor(BLACK)
        g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 120))
        g.drawString("Welcome to Poker", 160, 300)
    
    def setPanel: Unit = 
        contents = new FlowPanel {
            prepare
            listenTo(mouse.clicks)
            listenTo(mouse.moves)
            reactions += {
                case e: MouseClicked => processMouseClick(e)
                case h: MouseMoved => processMouseMoved(h)
            }
            preferredSize = new Dimension(WIDTH, HEIGHT)
            override def paintComponent(g: Graphics2D): Unit = 
                paintBackground(g)
                gameState match {
                    case GameEvent.INTRO => {
                        paintIntroduction(g)
                        buttonMap.get("IntroButton").get.repaint(g)
                    }
                    case GameEvent.START => {
                        combBoard.repaint(g, 0, null)
                        buttonMap.get("IntroButton").get.visible = false
                        cardRects.foreach(c => 
                            c.visible = true
                            c.repaint(g)
                        )
                        messageBoard.repaint(g)
                    }
                    case _ =>
                }
        }

    private def processMouseMoved(event : MouseMoved): Unit = 
        val point = event.point
        if(enteredButton(point) || enteredCards(point))
            cursor = handCursor
        else 
            cursor = defaultCursor
        repaint()


    private def processMouseClick(e : MouseClicked): Unit =
        if(combBoard.isEntered(e.point))
            dispose()
        else if(buttonMap.get("IntroButton").get.isEntered(e.point))
            buttonMap.get("IntroButton").get.clicked(controller)
    
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
