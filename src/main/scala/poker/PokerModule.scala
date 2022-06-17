package poker

import com.google.inject.AbstractModule
import controller.controllerBaseImpl.Controller
import controller.controller.ControllerInterface
import model.player.PlayerInterface
import model.player.playerBaseImpl.Player
import poker.model.card.CardInterface
import aview.gui.GUI
import aview.tui.TUI
import aview.UserInterface

class PokerModule extends AbstractModule:
    
    override def configure(): Unit = 
        bind(classOf[PlayerInterface]).toInstance(new Player(200))
        bind(classOf[ControllerInterface]).toInstance(new Controller(new Player(200)))
        //bind(classOf[UserInterface]).toInstance(new TUI(new Controller(new Player(200))))
        bind(classOf[UserInterface]).toInstance(new GUI(new Controller(new Player(200))))


