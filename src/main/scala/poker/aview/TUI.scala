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
        val input = readLine("Do you wanna quit (q) ?\n")
        input match {
            case "q" =>
            case _ => {
                start()
                controller.hasEnoughCredit() match 
                    case false => println("You dont have enough credit to play\n")
                    case true => gameLoop()
            }
        }
      
    def start() = 
        controller.clearUndoManager()
        var running = true
        createRound()
        while(running) {
            val state = controller.getStateOfRound().toString
            state match {
                case "Risk" => chooseRiskType()
                case "Bet" => setBet()
                case "Deal" => dealCards()
                case "Hold" => holdCards()
                case "End" => running = false
            }
        }

    def createRound() = controller.doAndPublish(controller.startRound, controller.createDeck())
    
    def chooseRiskType() = 
        val risk = readLine("\nWhich type of game you wanna play (LowRisk) or (HighRisk) ?\n")
        controller.doAndPublish(controller.chooseRiskType, risk)
        checkUndo()
    
    def setBet() = 
        val bet = readLine("\nPlease place your bet :\n").toInt
        controller.doAndPublish(controller.setBet, bet)
        checkUndo()
    
    def dealCards() = controller.doAndPublish(controller.dealCards())

    def holdCards() = 
        val input = processInput(readLine("\nWhich cards you wanna hold ?\n").split(" "))
        controller.doAndPublish(controller.holdCards, input)

    def checkUndo() = if(readLine("Undo ? (Y)(N)\n").equals("Y")) controller.undo()

    def processInput(input: Array[String]): Vector[Int] =
        var holded: Vector[Int] = Vector()
        input.foreach(i => if (!i.equals("")) holded = holded :+ i.toInt)
        holded
