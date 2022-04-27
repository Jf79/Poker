package poker
import model._
import view._
import poker.controller.Controller
import java.util.ResourceBundle.Control

object start :

    @main def run: Unit = 
      println("\nWelcome to Poker")
      val controller = new Controller()
      val tui = new TUI(controller)
      tui.run
  

