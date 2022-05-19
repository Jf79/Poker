package poker
package aview

import scala.io.StdIn._
import controller.Controller
import util._
import poker.model.BetState
import poker.model.StartState
import poker.model.RiskTypeState

class TUI(controller: Controller) extends Observer:
  controller.add(this)

  def run =
    println("\nWelcome to Poker\n")
    gameLoop()

  override def update = println(controller.toString)

  def gameLoop(): Unit =
    val input = readLine("Do you wanna quit (q) ?\n")
    input match
    case "q" =>
    case _ => {
      start()
      controller.hasEnoughCredit() match {
        case false => println("You dont have enough credit to play")
        case true => gameLoop()
      }
    }
      
  def start() = 
    var running = true
    createRound()
    val allStates : Array[State] = controller.getAllStates
    while(running) {
      val state = controller.getStateOfRound()
      if(state.equals(allStates(0))) 
        chooseRiskType()
      else if(state.equals(allStates(1))) 
        setBet()
      else if(state.equals(allStates(2))) 
        dealCards()
      else if(state.equals(allStates(3))) 
        holdCards()
      else if(state.equals(allStates(4))) 
        running = false
    }

  def createRound() = controller.doAndPublish(controller.startRound, controller.createDeck())
  
  def chooseRiskType() = 
    val risk = readLine("\nWhich type of game you wanna play (LowRisk) or (HighRisk) ?\n")
    controller.doAndPublish(controller.chooseRiskType, risk)
  
  def setBet() = 
    val bet = readLine("\nPlease place your bet :\n").toInt
    controller.doAndPublish(controller.setBet, bet)
  
  def dealCards() = controller.doAndPublish(controller.dealCards())

  def holdCards() = 
    val input = processInput(readLine("\nWhich cards you wanna hold ?\n").split(" "))
    controller.doAndPublish(controller.holdCards, input)

  def processInput(input: Array[String]): Vector[Int] =
    var holded: Vector[Int] = Vector()
    input.foreach(i => if (!i.equals("")) holded = holded :+ i.toInt)
    holded
