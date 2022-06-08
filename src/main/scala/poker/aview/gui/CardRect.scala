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
    val white = new Color(215, 215, 214, 255);
    val newWhite = new Color(249, 250, 248, 255);
    val normalStroke = new BasicStroke(8)
    val clickStroke = new BasicStroke(13)
    val symbolFont = new Font("Times Roman", Font.BOLD, 130)
    val pictureFont = new Font("Times Roman", Font.BOLD, 70)

    var isHolded = false
    var isClicked = false
    var cardIsSet = false
    var clickAble = false
    var stroke = normalStroke
    var backgroundColor = white
    var borderColor = color
    var green = new Color(36, 109, 18).brighter
    var cardColor = Color.BLACK
    var symbol: Option[Symbol] = None
    var picture: Option[Picture] = None

    def setCard(s: Symbol, p: Picture): CardRect = 
        if(s.equals(Symbol.HEART) || s.equals(Symbol.DIAMOND))
            cardColor = Color.RED.darker.darker
        symbol = Some(s)
        picture = Some(p)
        cardIsSet = true
        this

    def clicked(): Unit =
        if(!clickAble)
            return
        if(isClicked) 
            backgroundColor = white
            isClicked = false
            borderColor = color
            stroke = normalStroke
        else 
            backgroundColor = newWhite
            stroke = clickStroke
            isClicked = true
            borderColor = Color.CYAN.darker.darker

    def enter(): Unit = 
        if(!clickAble)
            return
        backgroundColor = newWhite

    def leaved(): Unit = 
        if(!clickAble)
            return
        if(!isClicked)
            backgroundColor = white

    def isEntered(p: Point): Boolean = p.x > topL.x && p.y > topL.y && p.x < topR.x && p.y < bottomL.y
    
    def areEquals(x: Symbol, y: Picture): Boolean = x.equals(symbol) && y.equals(picture)

    def repaint(g: Graphics2D) =
        g.setColor(borderColor)
        g.setStroke(stroke)
        g.drawRoundRect(topL.x, topL.y, width, height, edges, edges)
        g.setColor(backgroundColor)
        g.fillRoundRect(topL.x, topL.y, width, height, edges, edges)
        g.setColor(cardColor)
        g.setFont(symbolFont)
        g.drawString(symbol.get.paint,  topL.x + (width/3.5).toInt, topL.y + (height/1.4).toInt)   
        g.setFont(pictureFont) //topL.x + 10, topL.y + 80
        g.drawString(picture.get.toString, topL.x + 10, topL.y + 80)  

    def setVisible(b: Boolean): CardRect =
        visible = b
        this

    def setClickable(b: Boolean): CardRect =
        clickAble = b
        this
