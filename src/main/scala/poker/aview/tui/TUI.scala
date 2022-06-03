package poker
package aview

import scala.io.StdIn._
import util.GameEvent
import util._
import controller.controller.ControllerInterface

class TUI(controller: ControllerInterface) extends Observer:
    controller.add(this)

    def run =
        controller.startIntro()
        readLine()
        controller.startTheGame()

    override def update(event: GameEvent) = 
        event match {
            case GameEvent.INTRO => println("\nWelcome to Poker !\n")
            case GameEvent.START => startTheGame()
            case GameEvent.PLAY => {
                println(controller.toString)
                checkUndo()
                startTheRound()
            }
            case GameEvent.EXIT => {
                println("\nGoodbye\nHonor us again\n")
                sys.exit(0)
            }
        } 
    
    def startTheGame(): Unit = 
        val input = readLine("Do you wanna quit (q) ?\n")
        input match {
            case "q" => 
            case _ => {
                controller.clearUndoManager()
                createRound()
            }
        }

    def startTheRound() = 
        val state = controller.getStateOfRound().toString
        state match {
            case "Risk" => chooseRiskType()
            case "Bet" =>  setBet()
            case "Deal" => dealCards()
            case "Hold" => holdCards()
            case "Evaluation" => evaluation()
            case "End" => {
                checkCredit()
                controller.startTheGame()
            }
        }
        
    def createRound() = 
        controller.doAndPublish(controller.startRound, controller.createDeck())
    
    def chooseRiskType() = 
        val risk = readLine("\nWhich type of game you wanna play (LowRisk) or (HighRisk) ?\n")
        controller.doAndPublish(controller.chooseRiskType, risk)
    
    def setBet() = 
        val bet = processBetInput(readLine("\nPlease place your bet :\n"))
        controller.doAndPublish(controller.setBet, bet)
    
    def dealCards() = 
        controller.doAndPublish(controller.dealCards())

    def holdCards() = 
        val input = processInput(readLine("\nWhich cards you wanna hold ?\n").split(" "))
        controller.doAndPublish(controller.holdCards, input)

    def evaluation() = 
        controller.doAndPublish(controller.evaluation())
    
    def checkCredit() = 
        controller.hasEnoughCredit() match {
            case true => controller.startTheGame()
            case false => {
                println("You dont have enough credit to play\n")
                controller.endTheGame()
            }
        }
        
    def checkUndo() = 
        val s = controller.round.get.state
        if(s.toString.equals("Deal"))
            if(readLine("\nUndo ? (Y)(N)\n").equalsIgnoreCase("Y")) controller.undo()

    def processInput(input: Array[String]): Vector[Int] =
        var holded: Vector[Int] = Vector()
        input.foreach(i => if (!i.equals("")) holded = holded :+ i.toInt)
        holded

    def processBetInput(bet: String): Int = 
        if(bet.isEmpty) return 1
        return bet.toInt
