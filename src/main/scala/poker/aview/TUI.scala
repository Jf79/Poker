package poker
package aview

import scala.io.StdIn._
import model._
import model.CardsObject._
import util.Observer
import controller.Controller

class TUI(controller: Controller) extends Observer :

    def run = 
      controller.add(this)
      val player = new Player(500)
      gameLoop(player)
      println(player.toString)
    
    override def update = println(controller.toString)

    def gameLoop(player: Player) : Unit = 
      val input = readLine("Do you wanna quit (q) ?\n")
      input match 
        case "q" =>
        case _ => {
          println("The round started.\nGood luck.\n")
          startRound(new Round(player, deck = createCards(), bet = 10))
          gameLoop(player)
        }
      
    def startRound(round: Round) = 
      controller.setRound(round)
      controller.setPlayerCards()
      controller.holdCards(processInput(readLine("Which cards you wanna hold ?\n").split(" ")))

    def processInput(input: Array[String]) : Vector[Int] = 
      val holded = Vector()
      input.foreach(c => holded :+ c.toInt)
      holded
      
      

