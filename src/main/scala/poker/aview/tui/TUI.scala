package poker
package aview
package tui

import scala.io.StdIn._
import util.GameEvent
import util._
import controller.ControllerInterface

class TUI(controller: ControllerInterface) extends UserInterface:
    controller.add(this)

    def run =
        controller.startIntro()
        readLine
        controller.startTheGame()
        gameLoop()

    override def update(event: GameEvent) = 
        event match {
            case GameEvent.INTRO => println("\nWelcome to Poker !\n")
            case GameEvent.START => println("Do you wanna quit (q) ?\n")
            case GameEvent.PLAY => println(controller.toString)
            case GameEvent.EXIT => println("\nGoodbye\nHonor us again\n")
        }
    
    def gameLoop(): Unit = 
        val input = readLine
        input match {
            case "q" => controller.endTheGame()
            case _ => {
                startTheRound()
                gameLoop()
            }
        }

    def startTheRound() = 
        var running = true
        controller.clearUndoManager()
        createRound()
        while(running)
            val state = controller.getStateOfRound().toString
            state match {
                case "Risk" => chooseRiskType()
                case "Bet" =>  setBet()
                case "Deal" => dealCards()
                case "Hold" => holdCards()
                case "Evaluation" => evaluation()
                case "End" => {
                    checkCredit()
                    readLine
                    running = false
                }
            }
            checkUndo()
        
    def createRound() = 
        controller.doAndPublish(controller.startRound, controller.createDeck())
    
    def chooseRiskType() = 
        val risk = readLine("\nWhich type of game you want to play (LowRisk) or (HighRisk) ?\n")
        controller.doAndPublish(controller.chooseRiskType, risk)
    
    def setBet() = 
        val bet = processBetInput(readLine("\nPlease place your bet :\n"))
        controller.doAndPublish(controller.setBet, bet)
    
    def dealCards() = controller.doAndPublish(controller.dealCards())

    def holdCards() = 
        val input = processInput(readLine("\nWhich cards you wanna hold ?\n").split(" "))
        controller.doAndPublish(controller.holdCards, input)

    def evaluation() = controller.doAndPublish(controller.evaluation())
    
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
        if(s.toString.equals("Deal")||s.toString.equals("Bet"))
            if(readLine("\nUndo ? (Y)(N)\n").equalsIgnoreCase("Y")) controller.undo()

    def processInput(input: Array[String]): Vector[Int] =
        var holded: Vector[Int] = Vector()
        input.foreach(i => if (!i.equals("")) holded = holded :+ i.toInt)
        holded

    def processBetInput(bet: String): Int = 
        if(bet.isEmpty) return 1
        return bet.toInt
