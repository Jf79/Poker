package poker
package controller

import util.Command
import model.Round

class BetCommand(bet: Int) extends Command[Round]:

    var undo: Option[Round] = None
    var redo: Option[Round] = None

    def doStep(round: Round): Round =
        undo = Some(round.copyRound())
        val nRound = round.state.execute(round.setBet, bet).get
        redo = Some(nRound) 
        nRound

    def undoStep(round: Round): Round = 
        if(undo.isEmpty) return round
        undo.get
    def redoStep(round: Round): Round = 
        if(redo.isEmpty) return round
        redo.get

  

