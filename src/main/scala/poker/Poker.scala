package poker

import model._
import aview.TUI
import aview.GUI
import poker.controller.Controller
import java.util.ResourceBundle.Control

object start:

  @main def run: Unit =
    val player = new Player(10)
    val controller = new Controller(player)
    val tui = new TUI(controller)
    //tui.run
    val gui = new GUI(controller)
