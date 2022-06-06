package poker
package aview
package gui

import java.awt.Point
import java.awt.Color
import java.awt.Color._
import java.awt.Graphics2D
import java.awt.Font
import java.awt.BasicStroke
import controller.controller.ControllerInterface


case class IntroButton(topL: Point, topR: Point, bottomR: Point, bottomL: Point, color: Color, text: String) 
    extends MyButton:
    
    val stroke = new BasicStroke(1)
    var changedColor = color.darker

    def drawString(g: Graphics2D): Unit = 
        g.setColor(BLACK)
        g.setFont(new Font("Times Roman", Font.PLAIN, 70))
        g.drawString(text, topL.x + (width/10).toInt, bottomL.y - (height/4.5).toInt)


    def repaint(g: Graphics2D): Unit = 
        val edges = 40
        g.setColor(changedColor)
        g.fillRoundRect(topL.x, topL.y, width, height, edges, edges)
        g.setColor(BLACK)
        g.setStroke(stroke)
        g.drawRoundRect(topL.x, topL.y, width, height, edges, edges)
        drawString(g)


    override def isEntered(p: Point): Boolean = p.x > topL.x && p.y > topL.y && p.x < topR.x && p.y < bottomL.y
       
    override def enter(): Unit = changedColor = color

    override def leaved(): Unit = changedColor = color.darker

    override def clicked(c: ControllerInterface): Unit = 
        if(visible) c.startTheGame()

