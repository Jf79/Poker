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


class GUI(controller: ControllerInterface) extends Frame with Observer:
    controller.add(this)

    val WIDTH = 1400
    val HEIGHT = 800
    val backgroundColor = new Color(36, 109, 18) 
    val cardFont = new Font("TimesRoman", 0, 200)
    val textFont = new Font(Font.SERIF, Font.BOLD, 120)
    val displayStroke = new BasicStroke(5)

    var combBoard: CombinationBoard = null
    var introButton: IntroButton = null
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

    def prepare: Unit = 
        prepareIntroButton
        prepareCombBoard

    def prepareCombBoard: Unit = 
        val startX = WIDTH/50
        val startY = HEIGHT/50
        val xR = startX + (WIDTH/1.7).toInt // width 
        val yB = startY + (HEIGHT/2.3).toInt // height
        val topL = new Point(startX, startY)
        val topR = new Point(xR, startY)
        val bottomL = new Point(startX, yB)
        val bottomR = new Point(xR, yB)
        combBoard = new CombinationBoard(topL, topR, bottomR, bottomL, YELLOW)

    def prepareIntroButton: Unit = 
        val startX = (WIDTH/2.8).toInt
        val startY = (HEIGHT/2)
        val xR = startX + (WIDTH/5).toInt // width 
        val yB = startY + (HEIGHT/9).toInt // height
        val topL = new Point(startX, startY)
        val topR = new Point(xR, startY)
        val bottomL = new Point(startX, yB)
        val bottomR = new Point(xR, yB)
        introButton = new IntroButton(topL, topR, bottomR, bottomL, YELLOW, "Start")
        introButton.visible = true


    def paintBackground(g: Graphics2D): Unit =
        g.setColor(backgroundColor.darker)
        g.fillRect(0, 0, WIDTH, HEIGHT)

    def paintIntroduction(g: Graphics2D): Unit = 
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
                        introButton.repaint(g)
                    }
                    case GameEvent.START => combBoard.repaint(g, 0, null)
                    case _ =>
                }
        }

    def processMouseMoved(e : MouseMoved): Unit = 
        if(introButton.visible && introButton.isEntered(e.point))
            cursor = new Cursor(Cursor.HAND_CURSOR)
            introButton.changedColor = introButton.color
        else 
            cursor = new Cursor(Cursor.DEFAULT_CURSOR)
            introButton.changedColor = introButton.color.darker
        repaint()


    def processMouseClick(e : MouseClicked): Unit =
        if(combBoard.isEntered(e.point))
            dispose()
        else if(introButton.isEntered(e.point))
            introButton.clicked(controller)

