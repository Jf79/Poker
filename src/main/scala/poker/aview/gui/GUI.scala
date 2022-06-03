package poker
package aview

import java.awt.Dimension
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Stroke
import java.awt.BasicStroke
import java.awt.Color._

import scala.swing.FlowPanel
import scala.swing.Frame
import scala.swing.Font

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


class GUI(controller: ControllerInterface) extends Frame with Observer:
    controller.add(this)

    val WIDTH = 1400
    val HEIGHT = 800
    val backgroundColor = new Color(9, 71, 5) 
    val displayRect_height = 300
    val displayRect_width = 600
    val cardFont = new Font("TimesRoman", 0, 200)
    val textFont = new Font("TimesRoman",200, 100)
    val displayStroke = new BasicStroke(5)

    var start = false

    title = "Poker"
    visible = true
    size = new Dimension(WIDTH, HEIGHT)
    contents = new FlowPanel {
        preferredSize = new Dimension(WIDTH, HEIGHT)
        contents += new Button {
            this.text = "Start"
            this.background = YELLOW
            this.visible = true
            //this.location = new Point(500, 300)
            this.font = new Font("Start", 20, 100)
            listenTo(mouse.clicks)
            reactions += {
                case e: MouseClicked => {
                    repaint()
                    start = true
                    this.visible = false
                    repaint()
                }
            }
        }
        override def paintComponent(g: Graphics2D): Unit = 
            paintBackground(g)
            if(start)
                repaint()
                paintCombinationDisplay(g)
                paintCardRect(g)
            else
                paintIntroduction(g)
    }

    override def update(event: GameEvent) = ???

    def paintBackground(g: Graphics2D): Unit =
        g.setColor(backgroundColor)
        g.fillRect(0, 0, WIDTH, HEIGHT)

    def paintIntroduction(g: Graphics2D): Unit = 
        g.setColor(BLACK)
        g.setFont(textFont)
        g.drawString("Welcome to Poker !",200, 300)
        

    def paintCombinationDisplay(g: Graphics2D): Unit = 
        g.setColor(BLACK)
        g.setStroke(displayStroke)
        g.drawRoundRect(30, 30, 600, 300, 40, 40)
    
    def paintCardRect(g: Graphics2D): Unit = 
        g.setStroke(displayStroke)
        for (i <- 0 to 4)
            g.setColor(BLACK)  
            g.drawRoundRect(30 + (i*180), 400, 150, 250, 40, 40)
    
    def paintCard(g: Graphics2D, card: Card): Unit =
        g.setColor(getCardColor(card))

    def paintButton() = ???

    def getCardColor(card: Card): Color =
        if(card.symbol.equals(HEART) || card.symbol.equals(DIAMOND)) return RED
        BLACK

    /*for (i <- 0 to 4)
            g.setColor(WHITE)
            g.fillRoundRect(30 + (i*180), 400, 150, 250, 40, 40) */
