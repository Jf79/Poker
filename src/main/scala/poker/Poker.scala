package poker
import model._
import view._

object start {

    @main def run: Unit = {
    println("\nWelcome to Poker")
    val tui = new TUI()
    tui.run
    println("\nGoodbye")
  }
}

