package poker
package controller
package controllerBaseImpl

import util.Command
import model.round.RoundInterface

class ChooseRiskTypeCommand(riskType: String) extends Command[RoundInterface]{

    var undo: Option[RoundInterface] = None
    var redo: Option[RoundInterface] = None

    def doStep(round: RoundInterface): RoundInterface =
        undo = Some(round.copyRound())
        val nRound = round.state.execute(round.setRiskType, riskType).get
        redo = Some(nRound) 
        nRound
    def undoStep(round: RoundInterface): RoundInterface = 
        if(undo.isEmpty) return round
        undo.get
    def redoStep(round: RoundInterface): RoundInterface = 
        if(redo.isEmpty) return round
        redo.get

  


}
