package poker

import aview.TUI
import aview.GUI
import model.player.Player
import poker.controller.Controller

object start:

  @main def run: Unit =
    val player = new Player(10)
    val controller = new Controller(player)
    val tui = new TUI(controller)
    tui.run
