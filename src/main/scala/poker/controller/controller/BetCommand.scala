package poker
package controller

import util.Command
import model.round.RoundInterface

class BetCommand(bet: Int) extends Command[RoundInterface]:

    var undo: Option[RoundInterface] = None
    var redo: Option[RoundInterface] = None

    def doStep(round: RoundInterface): RoundInterface =
        undo = Some(round.copyRound())
        val nRound = round.state.execute(round.setBet, bet).get
        redo = Some(nRound) 
        nRound
    def undoStep(round: RoundInterface): RoundInterface = 
        if(undo.isEmpty) return round
        undo.get
    def redoStep(round: RoundInterface): RoundInterface = 
        if(redo.isEmpty) return round
        redo.get

  

