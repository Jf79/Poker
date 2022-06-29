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
import controller.ControllerInterface
import util.RiskType

case class MessageBoard(topL: Point, topR: Point, bottomR: Point, bottomL: Point, color: Color) 
    extends MyContent:

    val stroke = new BasicStroke(4)
    val edges = 30
    var combinationMessage: Option[String] = None

    def setCombination(c: Option[String]) = combinationMessage = c

    def repaint(g: Graphics2D, message: String): Unit =  
        g.setColor(Color.BLACK.brighter)
        g.fillRoundRect(topL.x, topL.y, topR.x - topL.x, bottomR.y - topR.y, edges, edges) 
        g.setStroke(stroke)
        g.setColor(color.darker)
        g.drawRoundRect(topL.x, topL.y, topR.x - topL.x, bottomR.y - topR.y, edges, edges)
        if(message != null)
            drawString(g, message)
        else if(combinationMessage.isDefined)
            drawString(g, combinationMessage.get)


    private def drawString(g: Graphics2D, message: String) = 
        val m = message.split("\n")
        g.setColor(WHITE.darker)
        g.setFont(new Font("Times Roman", Font.BOLD, 32))
        val space = (130/m.length).toInt
        for(i <- 0 until m.length)
            //g.drawString(m(i), topL.x + (width/5).toInt, topL.y + space + (i*50))
            g.drawString(m(i), topL.x + (width/4.8).toInt, topL.y + space + (i*50))


case class BoardRow(topL: Point, topR: Point, bottomR: Point, bottomL: Point, color: Color) 
    extends MyContent:
    
    val stroke = new BasicStroke(5)

    def repaint(g: Graphics2D): Unit =  
        g.setColor(Color.BLUE.darker)
        g.fillRect(topL.x, topL.y -2, topR.x - topL.x, bottomR.y - topR.y + 4)

case class BoardColumn(topL: Point, topR: Point, bottomR: Point, bottomL: Point, color: Color) 
    extends MyContent:

    def repaint(g: Graphics2D, stroke: BasicStroke, click: Boolean): Unit = 
        if(click)
            g.setColor(Color.RED.darker.darker)
            g.fillRect(topL.x, topL.y, width - 4, height)
        g.setColor(color.darker)
        g.setStroke(stroke)
        //printf("topL.x: %d, topL.y: %d, bottomL.x: %d, bottomL.y: %d\n",topL.x, topL.y, bottomL.x, bottomL.y)
        g.drawLine(topL.x, topL.y, bottomL.x, bottomL.y)


case class CombinationBoard(topL: Point, topR: Point, bottomR: Point, bottomL: Point, color: Color) 
    extends MyContent:

    val edges = 20
    val stroke = new BasicStroke(9)
    val rows: Array[BoardRow] = new Array(9)  
    val columns: Array[BoardColumn] = new Array(6) 
    val columnHeight: Int = height
    val columnCombWidth: Int = (width/3.8).toInt
    val columnWidth: Int = ((width-columnCombWidth)/(columns.length - 1)).toInt
    val rowWidth: Int = width
    val rowHeight: Int = (height/ rows.length).toInt
    var riskType: Option[RiskType] = None
    var combination: Option[Combination] = None

    def setCombination(comb: Option[Combination]) =
        combination = comb

    def create(): CombinationBoard = 
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
        rows(8).bottomL.y = rows(8).bottomL.y
        rows(8).bottomR.y = rows(8).bottomR.y
        this

    def isEntered(p: Point): Boolean = 
        p.x > topL.x && p.y > topL.y && p.x < topR.x && p.y < bottomL.y
    
    def repaint(g: Graphics2D, click: Option[Int], c: ControllerInterface): Unit = 
        if(!c.round.isEmpty) riskType = c.round.get.riskType
        g.setColor(Color.BLACK)
        g.fillRoundRect(topL.x, topL.y, width, height, edges, edges)
        g.setColor(BLUE)
        if(combination.isDefined)
            if(combination.get.getRank < 10) //riskType.get.lowestCombination.equals("Pair")
                rows(combination.get.getRank - 1).repaint(g)
        columns.foreach(c => if(columns.indexOf(c) != 0) c.repaint(g, stroke, false))
        if(click.isDefined) columns(click.get + 1).repaint(g, stroke, true)
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
            var combName = comb(10 - i).getName
            if(!riskType.isEmpty && i == 9)
                combName = riskType.get.lowestCombination
            g.drawString(combName, topL.x + 10, rows(i - 1).bottomL.y - 10)



