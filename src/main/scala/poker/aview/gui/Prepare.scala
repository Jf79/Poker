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
        val startX = combBoard.topR.x + 30
        val startY = combBoard.topR.y
        val xR = startX + (WIDTH/3.6).toInt // width 
        val yB = startY + (combBoard.height/2.1).toInt // height
        val topL = new Point(startX, startY)
        val topR = new Point(xR, startY)
        val bottomL = new Point(startX, yB)
        val bottomR = new Point(xR, yB)
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
        new CombinationBoard(topL, topR, bottomR, bottomL, YELLOW).create()

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

    def prepareFirstButtons(WIDTH: Int, HEIGHT: Int, combBoard: CombinationBoard, buttonMap: mutable.Map[String, MyButton]): Unit = 
        val startX = combBoard.topR.x + 230
        val startX2 = combBoard.topR.x + 50
        val startY = combBoard.topR.y + (combBoard.height/0.96).toInt
        val xR = startX + (WIDTH/8).toInt // width 
        val xR2 = startX2 + (WIDTH/8).toInt // width 
        val yB = startY + (combBoard.height/4).toInt // height
        val topL = new Point(startX, startY)
        val topL2 = new Point(startX2, startY)
        val topR = new Point(xR, startY)
        val topR2 = new Point(xR2, startY)
        val bottomL = new Point(startX, yB)
        val bottomL2 = new Point(startX2, yB)
        val bottomR = new Point(xR, yB)
        val bottomR2 = new Point(xR2, yB)

        buttonMap.put("BackButton",new BackButton(topL, topR, bottomR, bottomL, RED.darker, "BACK"))
        buttonMap.put("HighButton", new HighButton(topL, topR, bottomR, bottomL, RED.darker, "HIGH"))
        buttonMap.put("ExitButton", new ExitButton(topL, topR, bottomR, bottomL, RED, "NO"))
        buttonMap.put("StartButton",  new StartButton(topL2, topR2, bottomR2, bottomL2, YELLOW, "YES"))
        buttonMap.put("LowButton", new LowButton(topL2, topR2, bottomR2, bottomL2, YELLOW, "LOW"))
        buttonMap.put("CoinButton", new CoinButton(topL2, topR2, bottomR2, bottomL2, YELLOW, "COIN"))

    def prepareDealButton(WIDTH: Int, HEIGHT: Int, combBoard: CombinationBoard, buttonMap: mutable.Map[String, MyButton]): Unit = 
        val startX = combBoard.topR.x + 230
        val startY = combBoard.topR.y + (combBoard.height/1.3).toInt
        val xR = startX + (WIDTH/8).toInt // width 
        val yB = startY + (combBoard.height/4).toInt // height
        val topL = new Point(startX, startY)
        val topR = new Point(xR, startY)
        val bottomL = new Point(startX, yB)
        val bottomR = new Point(xR, yB)
        buttonMap.put("DealButton", new DealButton(topL, topR, bottomR, bottomL, ORANGE, "DEAL"))
    
    def prepareCards(WIDTH: Int, HEIGHT: Int): Array[CardRect] =
        val startX = (WIDTH/50).toInt
        val startY = (HEIGHT/2).toInt
        val cardWidth = 190
        val cardHeight = 270
        val space = 30
        val cardRects: Array[CardRect] = new Array(5)
        //val hand = getRandomCards(controller.createDeck(), 5)._1
        for(i <- 0 until cardRects.length)
            val pointX = startX + i * (cardWidth + space)
            val topL = new Point(pointX, startY)
            val topR = new Point(pointX + cardWidth, startY)
            val bottomR = new Point(pointX + cardWidth, startY + cardHeight)
            val bottomL = new Point(pointX , startY + cardHeight)
            cardRects(i) = new CardRect(topL, topR, bottomR, bottomL, BLACK)
        cardRects

