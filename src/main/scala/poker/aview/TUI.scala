package poker
package aview

import scala.io.StdIn._
import controller.Controller
import util._

class TUI(controller: Controller) extends Observer:
  controller.add(this)

  def run =
    println("\nWelcome to Poker\n")
    gameLoop()

  override def update = println(controller.toString)

  def gameLoop(): Unit =
    controller.hasEnoughCredit() match {
      case false => println("You dont have enough credit to play")
      case true => {
        val input = readLine("Do you wanna quit (q) ?\n")
        input match
        case "q" =>
        case _ => {
          executeRound(10, "low")
          gameLoop()
        } 
      }
    } 

  def executeRound(bet: Int, riskType: String) =
    controller.doAndPublish(controller.createRound, controller.createDeck())
    controller.doAndPublish(controller.setBet, bet)
    controller.doAndPublish(controller.dealCards())
    controller.doAndPublish(
      controller.holdCards,
      processInput(readLine("\nWhich cards you wanna hold ?\n").split(" "))
    )

  def processInput(input: Array[String]): Vector[Int] =
    var holded: Vector[Int] = Vector()
    input.foreach(i => if (!i.equals("")) holded = holded :+ i.toInt)
    holded
