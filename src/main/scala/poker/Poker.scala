package poker
import model._
import aview.TUI
import poker.controller.Controller
import java.util.ResourceBundle.Control

object start:

  @main def run: Unit =
    println("\nWelcome to Poker")
    val player = new Player(100)
    val controller = new Controller(player)
    val tui = new TUI(controller)
    tui.run
