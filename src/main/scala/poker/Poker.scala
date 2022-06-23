package poker

import com.google.inject.{Guice, Injector, Inject}

import aview.tui.TUI
import aview.gui.GUI
import model.player.playerBaseImpl.Player
import controller.controllerBaseImpl.Controller
import poker.model.player.PlayerInterface
import poker.controller.ControllerInterface
import poker.aview.UserInterface

object Poker:

  @main def run: Unit =
    val injector = Guice.createInjector(new PokerModule)
    val player = injector.getInstance(classOf[PlayerInterface])
    val controller = injector.getInstance(classOf[ControllerInterface])
    val ui = injector.getInstance(classOf[UserInterface])
    ui.run
