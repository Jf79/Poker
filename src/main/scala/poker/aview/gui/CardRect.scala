package poker
package aview
package gui

import java.awt.Point
import java.awt.Color
import java.awt.Graphics2D
import java.awt.BasicStroke
import poker.util.Picture
import poker.util.Symbol
import java.awt.Font



case class CardRect(topL: Point, topR: Point, bottomR: Point, bottomL: Point, color: Color) 
    extends MyContent:

    val edges = 30
    val darkWhite = Color.WHITE.darker
    val stroke = new BasicStroke(5)
    val symbolFont = new Font("Times Roman", Font.BOLD, 130)
    val pictureFont = new Font("Times Roman", Font.BOLD, 70)

    var isHolded = false
    var isClicked = false
    visible = false
    var backgroundColor = darkWhite
    var borderColor = color
    var cardColor = Color.BLACK
    var symbol: Symbol = null
    var picture: Picture = null

    def setCard(s: Symbol, p: Picture): Unit = 
        if(s.equals(Symbol.HEART) || s.equals(Symbol.DIAMOND))
            cardColor = Color.RED.darker
        symbol = s
        picture = p

    def clicked(): Unit = 
        if(isClicked) 
            borderColor = Color.RED
            isClicked = false
        else 
            isClicked = true
            borderColor = color

    def enter(): Unit = backgroundColor = darkWhite.brighter

    def leaved(): Unit = backgroundColor = darkWhite

    def isEntered(p: Point): Boolean = p.x > topL.x && p.y > topL.y && p.x < topR.x && p.y < bottomL.y
        
    def repaint(g: Graphics2D) =
        g.setColor(backgroundColor)
        g.fillRoundRect(topL.x, topL.y, width, height, edges, edges)
        g.setColor(borderColor)
        g.setStroke(stroke)
        g.drawRoundRect(topL.x, topL.y, width, height, edges, edges)
        g.setColor(cardColor)
        g.setFont(symbolFont)
        g.drawString(symbol.paint,  topL.x + (width/3.5).toInt, topL.y + (height/1.4).toInt)   
        g.setFont(pictureFont) //topL.x + 10, topL.y + 80
        g.drawString(picture.toString, topL.x + 10, topL.y + 80)     

