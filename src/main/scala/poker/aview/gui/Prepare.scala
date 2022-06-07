package poker
package aview
package gui
import util.CardsObject._
import java.awt.Point

import controller.controller.ControllerInterface
import java.awt.Color._
import scala.collection.mutable
import scala.collection.mutable.HashMap

object Prepare:

    def prepareMessageBoard(WIDTH: Int, HEIGHT: Int, combBoard: CombinationBoard): MessageBoard =
        val startX = combBoard.topR.x + 50
        val startY = combBoard.topR.y
        val xR = startX + (WIDTH/4).toInt // width 
        val yB = startY + (combBoard.height/2.3).toInt // height
        val topL = new Point(startX, startY)
        val topR = new Point(xR, startY)
        val bottomL = new Point(startX, yB)
        val bottomR = new Point(xR, yB)
        //buttonMap.put("MessageBoard", messageBoard)
        new MessageBoard(topL, topR, bottomR, bottomL, YELLOW)

            
    def prepareCombBoard(WIDTH: Int, HEIGHT: Int): CombinationBoard = 
        val startX = WIDTH/50
        val startY = HEIGHT/50
        val xR = startX + (WIDTH/1.7).toInt // width 
        val yB = startY + (HEIGHT/2.3).toInt // height
        val topL = new Point(startX, startY)
        val topR = new Point(xR, startY)
        val bottomL = new Point(startX, yB)
        val bottomR = new Point(xR, yB)
        new CombinationBoard(topL, topR, bottomR, bottomL, YELLOW)

    def prepareIntroButton(WIDTH: Int, HEIGHT: Int, buttonMap: mutable.Map[String, MyButton]): Unit = 
        val startX = (WIDTH/2.8).toInt
        val startY = (HEIGHT/2).toInt
        val xR = startX + (WIDTH/5).toInt // width 
        val yB = startY + (HEIGHT/9).toInt // height
        val topL = new Point(startX, startY)
        val topR = new Point(xR, startY)
        val bottomL = new Point(startX, yB)
        val bottomR = new Point(xR, yB)
        val introButton = new IntroButton(topL, topR, bottomR, bottomL, YELLOW, "START")
        buttonMap.put("IntroButton", introButton)
    
    def prepareExitButton(WIDTH: Int, HEIGHT: Int,buttonMap: mutable.Map[String, MyButton], combBoard: CombinationBoard): Unit = 
        val startX = combBoard.topR.x + 230
        val startY = combBoard.topR.y + (combBoard.height/1.5).toInt
        val xR = startX + (WIDTH/8).toInt // width 
        val yB = startY + (combBoard.height/3.2).toInt // height
        val topL = new Point(startX, startY)
        val topR = new Point(xR, startY)
        val bottomL = new Point(startX, yB)
        val bottomR = new Point(xR, yB)
        val exitButton = new ExitButton(topL, topR, bottomR, bottomL, RED, "NO")
        exitButton.visible = false
        buttonMap.put("ExitButton", exitButton)
    
    def prepareStartButton(WIDTH: Int, HEIGHT: Int, combBoard: CombinationBoard, buttonMap: mutable.Map[String, MyButton]): Unit = 
        val startX = combBoard.topR.x + 40
        val startY = combBoard.topR.y + (combBoard.height/1.5).toInt
        val xR = startX + (WIDTH/8).toInt // width 
        val yB = startY + (combBoard.height/3.2).toInt // height
        val topL = new Point(startX, startY)
        val topR = new Point(xR, startY)
        val bottomL = new Point(startX, yB)
        val bottomR = new Point(xR, yB)
        val startButton = new StartButton(topL, topR, bottomR, bottomL, YELLOW, "YES")
        startButton.visible = false
        buttonMap.put("StartButton", startButton)

    def prepareCards(WIDTH: Int, HEIGHT: Int): Array[CardRect] =
        val startX = (WIDTH/50).toInt
        val startY = (HEIGHT/2).toInt
        val cardWidth = 190
        val cardHeight = 270
        val space = 30
        val cardRects: Array[CardRect] = new Array(5)
       // val hand = getRandomCards(controller.createDeck(), 5)._1
        for(i <- 0 until cardRects.length)
            val pointX = startX + i * (cardWidth + space)
            val topL = new Point(pointX, startY)
            val topR = new Point(pointX + cardWidth, startY)
            val bottomR = new Point(pointX + cardWidth, startY + cardHeight)
            val bottomL = new Point(pointX , startY + cardHeight)
            cardRects(i) = new CardRect(topL, topR, bottomR, bottomL, BLACK)
            //cardRects(i).setCard(hand(i).symbol, hand(i).picture)
        cardRects

