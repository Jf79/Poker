package poker
package aview

import scala.io.StdIn._
import controller.Controller
import poker.util._
import poker.model.ReplaceState

class TUI(controller: Controller) extends Observer:
  controller.add(this)

  def run =
    gameLoop()

  override def update = println(controller.toString)

  def gameLoop(): Unit =
    val input = readLine("Do you wanna quit (q) ?\n")
    input match
      case "q" =>
      case _ => {
        println("\nThe round started.\n")
        startRound(10)
        gameLoop()
      }

  def startRound(bet: Int) =
    controller.doAndPublish(controller.createRound, controller.createDeck())
    controller.handle(BetEvent())
    controller.doAndPublish(controller.setBet, bet)
    controller.handle(StartEvent())
    controller.doAndPublish(controller.start())
    controller.handle(ReplaceEvent())
    controller.doAndPublish(
      controller.holdCards,
      processInput(readLine("\nWhich cards you wanna hold ?\n").split(" "))
    )

  def processInput(input: Array[String]): Vector[Int] =
    var holded: Vector[Int] = Vector()
    input.foreach(i => if (!i.equals("")) holded = holded :+ i.toInt)
    holded
