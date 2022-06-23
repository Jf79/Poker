package poker
package aview
package gui

import java.awt.Point
import java.awt.Color
import java.awt.Color._
import java.awt.Graphics2D
import java.awt.Font
import java.awt.BasicStroke
import controller.ControllerInterface


case class IntroButton(topL: Point, topR: Point, bottomR: Point, bottomL: Point, color: Color, text: String) 
    extends MyButton:
    
    val stroke = new BasicStroke(2)
    changedColor = color.darker

    def drawString(g: Graphics2D): Unit = 
        g.setColor(BLACK)
        g.setFont(new Font("Times Roman", Font.BOLD, 60))
        g.drawString(text, topL.x + (width/7.5).toInt, bottomL.y - (height/4).toInt)


    def repaint(g: Graphics2D): Unit = 
        val edges = 40
        g.setColor(changedColor)
        g.fillRoundRect(topL.x, topL.y, width, height, edges, edges)
        g.setColor(BLACK)
        g.setStroke(stroke)
        g.drawRoundRect(topL.x, topL.y, width, height, edges, edges)
        drawString(g)

    override def clicked(c: ControllerInterface): Unit = 
        if(visible) c.startTheGame()

case class ExitButton(topL: Point, topR: Point, bottomR: Point, bottomL: Point, color: Color, var text: String) 
    extends MyButton:
    
    val stroke = new BasicStroke(3)
    changedColor = color.darker

    def repaint(g: Graphics2D): Unit = 
        val edges = 40
        g.setColor(changedColor)
        g.fillRoundRect(topL.x, topL.y, width, height, edges, edges)
        g.setColor(BLACK)
        g.setStroke(stroke)
        g.drawRoundRect(topL.x, topL.y, width, height, edges, edges)
        drawString(g)

    def setText(s: String): ExitButton = 
        text = s
        this

    private def drawString(g: Graphics2D): Unit = 
        g.setColor(BLACK)
        g.setFont(new Font("Times Roman", Font.BOLD, 40))
        g.drawString(text, topL.x + (width/4.4).toInt, bottomL.y - (height/3.3).toInt)

    override def clicked(c: ControllerInterface): Unit = 
        if(visible) c.endTheGame()

case class StartButton(topL: Point, topR: Point, bottomR: Point, bottomL: Point, color: Color, text: String) 
    extends MyButton:
    
    val stroke = new BasicStroke(3)
    changedColor = color.darker

    def repaint(g: Graphics2D): Unit = 
        val edges = 40
        g.setColor(changedColor)
        g.fillRoundRect(topL.x, topL.y, width, height, edges, edges)
        g.setColor(BLACK)
        g.setStroke(stroke)
        g.drawRoundRect(topL.x, topL.y, width, height, edges, edges)
        drawString(g)

    private def drawString(g: Graphics2D): Unit = 
        g.setColor(BLACK)
        g.setFont(new Font("Times Roman", Font.BOLD, 40))
        g.drawString(text, topL.x + (width/5.2).toInt, bottomL.y - (height/3.3).toInt)
    
    override def clicked(c: ControllerInterface): Unit = 
        if(visible) 
            c.doAndPublish(c.startRound, c.createDeck())

case class LowButton(topL: Point, topR: Point, bottomR: Point, bottomL: Point, color: Color, text: String) 
    extends MyButton:
    
    val stroke = new BasicStroke(3)
    changedColor = color.darker

    def repaint(g: Graphics2D): Unit = 
        val edges = 40
        g.setColor(changedColor)
        g.fillRoundRect(topL.x, topL.y, width, height, edges, edges)
        g.setColor(BLACK)
        g.setStroke(stroke)
        g.drawRoundRect(topL.x, topL.y, width, height, edges, edges)
        drawString(g)

    private def drawString(g: Graphics2D): Unit = 
        g.setColor(BLACK)
        g.setFont(new Font("Times Roman", Font.BOLD, 40))
        g.drawString(text, topL.x + (width/5.3).toInt, bottomL.y - (height/3.3).toInt)

    override def clicked(c: ControllerInterface): Unit = 
        if(visible) c.doAndPublish(c.chooseRiskType,"low")
    
case class HighButton(topL: Point, topR: Point, bottomR: Point, bottomL: Point, color: Color, text: String) 
    extends MyButton:
    
    val stroke = new BasicStroke(3)
    changedColor = color.darker

    def repaint(g: Graphics2D): Unit = 
        val edges = 40
        g.setColor(changedColor)
        g.fillRoundRect(topL.x, topL.y, width, height, edges, edges)
        g.setColor(BLACK)
        g.setStroke(stroke)
        g.drawRoundRect(topL.x, topL.y, width, height, edges, edges)
        drawString(g)

    private def drawString(g: Graphics2D): Unit = 
        g.setColor(BLACK)
        g.setFont(new Font("Times Roman", Font.BOLD, 40))
        g.drawString(text, topL.x + (width/5.3).toInt, bottomL.y - (height/3.3).toInt)

    override def clicked(c: ControllerInterface): Unit = 
        if(visible) c.doAndPublish(c.chooseRiskType, "high")


case class BackButton(topL: Point, topR: Point, bottomR: Point, bottomL: Point, color: Color, text: String) 
    extends MyButton:
    
    val stroke = new BasicStroke(3)
    changedColor = color.darker

    def repaint(g: Graphics2D): Unit = 
        val edges = 40
        g.setColor(changedColor)
        g.fillRoundRect(topL.x, topL.y, width, height, edges, edges)
        g.setColor(BLACK)
        g.setStroke(stroke)
        g.drawRoundRect(topL.x, topL.y, width, height, edges, edges)
        drawString(g)

    private def drawString(g: Graphics2D): Unit = 
        g.setColor(BLACK)
        g.setFont(new Font("Times Roman", Font.BOLD, 40))
        g.drawString(text, topL.x + (width/7).toInt, bottomL.y - (height/3.3).toInt)

    override def clicked(c: ControllerInterface): Unit = 
        if(visible) c.undo()
    
case class CoinButton(topL: Point, topR: Point, bottomR: Point, bottomL: Point, color: Color, text: String) 
    extends MyButton:
    
    val stroke = new BasicStroke(3)
    var clicks = 0

    changedColor = color.darker
    def repaint(g: Graphics2D): Unit = 
        val edges = 40
        g.setColor(changedColor)
        g.fillRoundRect(topL.x, topL.y, width, height, edges, edges)
        g.setColor(BLACK)
        g.setStroke(stroke)
        g.drawRoundRect(topL.x, topL.y, width, height, edges, edges)
        drawString(g)
    
    def getClick: Int = clicks % 5

    private def drawString(g: Graphics2D): Unit = 
        g.setColor(BLACK)
        g.setFont(new Font("Times Roman", Font.BOLD, 40))
        g.drawString(text, topL.x + (width/5.6).toInt, bottomL.y - (height/3.3).toInt)

    override def clicked(c: ControllerInterface): Unit = 
        if(visible) clicks += 1

case class DealButton(topL: Point, topR: Point, bottomR: Point, bottomL: Point, color: Color, text: String) 
    extends MyButton:
    
    var holdState = false
    var button: CoinButton = null
    var cardRects: Array[CardRect] = null
    
    var gui: GUI = null
    val stroke = new BasicStroke(3)

    changedColor = color.darker
    def repaint(g: Graphics2D): Unit = 
        val edges = 40
        g.setColor(changedColor)
        g.fillRoundRect(topL.x, topL.y, width, height, edges, edges)
        g.setColor(BLACK)
        g.setStroke(stroke)
        g.drawRoundRect(topL.x, topL.y, width, height, edges, edges)
        drawString(g)
    
    private def drawString(g: Graphics2D): Unit = 
        g.setColor(BLACK)
        g.setFont(new Font("Times Roman", Font.BOLD, 40))
        g.drawString(text, topL.x + (width/6).toInt, bottomL.y - (height/3.3).toInt)

    def setCoinButton(b: CoinButton): DealButton = 
        button = b
        this

    def setHoldState(b: Boolean): DealButton = 
        holdState = b
        this
    
    def setCardRects(g: GUI): DealButton =
        gui = g
        cardRects = g.cardRects.get
        this
    
    private def processCardRects: Vector[Int] =
        var vector: Vector[Int] = Vector()
        cardRects.foreach(card =>
            if(card.isClicked) 
                vector = vector :+ (cardRects.indexOf(card) + 1)
                card.isHolded = true
        )
        gui.cardsPainted = Some(false)
        gui.numberOfCardsPainted = Some(1)
        vector

    override def clicked(controller: ControllerInterface): Unit = 
        if(visible && !holdState) 
            val bet = controller.round.get.riskType.get.getMinimumBet * (button.getClick + 1)
            controller.doAndPublish(controller.setBet, bet)
            if(!controller.round.get.failed)
                controller.doAndPublish(controller.dealCards())
        else if(visible && holdState)
            controller.doAndPublish(controller.holdCards, processCardRects)
            leaved()
            gui.repaint()
            
