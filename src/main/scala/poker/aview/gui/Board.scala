package poker
package aview
package gui

import java.awt.Font
import java.awt.Point
import java.awt.Color
import java.awt.Color._
import java.awt.BasicStroke
import java.awt.Graphics2D

import util.Combination

case class MessageBoard(topL: Point, topR: Point, bottomR: Point, bottomL: Point, color: Color) 
    extends MyContent:

    val stroke = new BasicStroke(4)
    val edges = 30

    def repaint(g: Graphics2D, message: String): Unit =  
        g.setColor(Color.BLACK.brighter)
        g.fillRoundRect(topL.x, topL.y, topR.x - topL.x, bottomR.y - topR.y, edges, edges) 
        g.setStroke(stroke)
        g.setColor(color.darker)
        g.drawRoundRect(topL.x, topL.y, topR.x - topL.x, bottomR.y - topR.y, edges, edges)
        if(message != null)
            drawString(g, message)

    private def drawString(g: Graphics2D, message: String) = 
        val m = message.split("\n")
        println(m.toString)
        g.setColor(WHITE.darker)
        g.setFont(new Font("Times Roman", Font.BOLD, 45))
        val space = (130/m.length).toInt
        for(i <- 0 until m.length)
            g.drawString(m(i), topL.x + (width/12).toInt, topL.y + space + (i*50))
            g.drawString(m(i), topL.x + (width/12).toInt, topL.y + space + (i*50))


case class BoardRow(topL: Point, topR: Point, bottomR: Point, bottomL: Point, color: Color) 
    extends MyContent:
    
    val stroke = new BasicStroke(4)

    def repaint(g: Graphics2D): Unit =  
        g.setColor(Color.BLACK)
        g.fillRect(topL.x, topL.y, topR.x - topL.x, bottomR.y - topR.y)
        g.setColor(color)
        g.setStroke(stroke)
        g.drawRect(topL.x, topL.y, topR.x - topL.x, bottomR.y - topR.y)

case class BoardColumn(topL: Point, topR: Point, bottomR: Point, bottomL: Point, color: Color) 
    extends MyContent:

    def repaint(g: Graphics2D, stroke: BasicStroke, click: Int): Unit = 
        /*if(click > 1)
            g.setColor(Color.RED.darker)
            g.fillRect(topL.x, topL.y, width, height)*/
        g.setColor(color.darker)
        g.setStroke(stroke)
        //printf("topL.x: %d, topL.y: %d, bottomL.x: %d, bottomL.y: %d\n",topL.x, topL.y, bottomL.x, bottomL.y)
        g.drawLine(topL.x, topL.y, bottomL.x, bottomL.y)


case class CombinationBoard(topL: Point, topR: Point, bottomR: Point, bottomL: Point, color: Color) extends MyContent:

    val stroke = new BasicStroke(6)
    val rows: Array[BoardRow] = new Array(9)  
    val columns: Array[BoardColumn] = new Array(6) 
    val columnHeight: Int = height
    val columnCombWidth: Int = (width/3.8).toInt
    val columnWidth: Int = ((width-columnCombWidth)/(columns.length - 1)).toInt
    val rowWidth: Int = width
    val rowHeight: Int = (height/ rows.length).toInt

    def create(): Unit = 
        for(i <- 0 until rows.length)
            val tL = new Point(topL.x, (topL.y + i * rowHeight))
            val tR = new Point(topR.x, (topL.y + i * rowHeight))
            val bR = new Point(topR.x, (topR.y + ((i + 1) * rowHeight)))
            val bL = new Point(topL.x, (topR.y + ((i + 1) * rowHeight)))
            rows(i) = new BoardRow(tL, tR, bR, bL, color)

        columns(0) = new BoardColumn(new Point(topL.x, topL.y), new Point(topL.x + columnCombWidth, topR.y), 
        new Point(topL.x + columnCombWidth, bottomR.y), new Point(bottomL.x, bottomL.y), color) 
        for(i <- 1 until columns.length)
            val tL = new Point(columns(0).topR.x + (i-1) * columnWidth, topL.y)
            val tR = new Point(columns(0).topR.x + i * columnWidth, topR.y)
            val bR = new Point(columns(0).bottomR.x + i * columnWidth, bottomR.y)
            val bL = new Point(columns(0).bottomR.x + (i-1) * columnWidth, bottomL.y)
            columns(i) = new BoardColumn(tL, tR, bR, bL, color)
        rows(8).bottomL.y = rows(8).bottomL.y + 5
        rows(8).bottomR.y = rows(8).bottomR.y + 5

    def isEntered(p: Point): Boolean = 
        p.x > topL.x && p.y > topL.y && p.x < topR.x && p.y < bottomL.y
    
    def repaint(g: Graphics2D, bet: Int, combination: Combination): Unit = 
        //println("columnCombWidth: " + columnCombWidth)
        //println("columnWidth " + columnWidth)
        create()
        val edges = 20
        g.setColor(color)
        g.setColor(Color.BLACK)
        g.fillRoundRect(topL.x, topL.y, width, height, edges, edges)
        if(combination != null)
            if(combination.getRank < 10)
                rows(combination.getRank).repaint(g)
        columns.foreach(c => if(columns.indexOf(c) != 0) c.repaint(g, stroke, 2))
        drawStrings(g)
        drawValues(g)
        g.setColor(color.darker)
        g.setStroke(stroke)
        g.drawRoundRect(topL.x, topL.y, width, height, edges, edges)

    private def drawValues(g: Graphics2D): Unit = 
        g.setColor(color.darker)
        g.setFont(new Font("Time New Roman", Font.BOLD, 25))
        val comb = Combination.values
        for(j <- 1 until columns.length)
            for(i <- 1 until comb.length)
                g.drawString((comb(10 - i).getMultFactor * j).toString, (columns(j).bottomL.x) + 20, rows(i - 1).bottomL.y - 10)
    
    private def drawStrings(g: Graphics2D): Unit =
        g.setColor(color.darker)
        g.setFont(new Font("Time New Roman", Font.BOLD, 25))
        val comb = Combination.values
        for(i <- 1 until comb.length)
           g.drawString(comb(10 - i).getName, topL.x + 10, rows(i - 1).bottomL.y - 10)



