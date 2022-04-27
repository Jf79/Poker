package poker
package view

import scala.io.StdIn._
import model._
import model.CardsObject._
import util.Observer
import controller.Controller

class TUI(controller: Controller) extends Observer :
    controller.add(this)

    def run = 
      val player = new Player(500)
      gameLoop(player)
      println(player.toString)
    
    override def update = println(controller.toString)

    def gameLoop(player: Player) : Unit = 
      val input = readLine("Do you wanna quit (q) ?")
      input match 
        case "q" =>
        case _ => {
          startRound(new Round(player, deck = createCards(), bet = 10))
          gameLoop(player)
        }
      
    def startRound(round: Round) = 
      controller.setRound(round)
      controller.holdCards(processInput(readLine("Which cards you wanna hold ?").split(" ")))

    def processInput(input: Array[String]) : Vector[Int] = 
      val holded = Vector()
      input.foreach(c => holded :+ c.toInt)
      holded
      
      

