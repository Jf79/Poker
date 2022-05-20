package poker
package aview

import scala.io.StdIn._
import controller.Controller
import util._
import poker.model.BetState
import poker.model.StartState
import poker.model.RiskTypeState
import poker.model.DealCardsState
import poker.model.HoldCardsState
import poker.model.EndState

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
    val states : Array[State] = controller.getAllStates
    while(running) {
      if(readLine("Undo ? (J)(N)").equalsIgnoreCase("J")) controller.undo()
      val state = controller.getStateOfRound()
      if(state.getClass.equals(states(0).getClass)) 
        chooseRiskType()
      else if(state.getClass.equals(states(1).getClass)) 
        setBet()
      else if(state.getClass.equals(states(2).getClass)) 
        dealCards()
      else if(state.getClass.equals(states(3).getClass)) 
        holdCards()
      else if(state.getClass.equals(states(4).getClass)) 
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
