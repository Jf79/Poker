package poker
import model._
import aview._
import poker.controller.Controller
import java.util.ResourceBundle.Control

object start :

    @main def run: Unit = 
      println("\nWelcome to Poker")
      val controller = new Controller()
      val tui = new TUI(controller)
      tui.run
  

