package poker

import aview.tui.TUI
import aview.gui.GUI
import model.player.Player
import controller.controller.Controller

object start:

  @main def run: Unit =
    val player = new Player(2000)
    val controller = new Controller(player)
    val tui = new TUI(controller)
    val gui = new GUI(controller)
    //gui.run
    tui.run
