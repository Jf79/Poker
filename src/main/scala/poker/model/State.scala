package poker
package model

import util.Event

trait Stateable:
    var state: Option[State] = None
    def handle(e: Event): Option[State]

trait State:
    def toString(): String

case class BetState(round: Round) extends State :
    override def toString() = "\n" + round.gameType.message + "\nyour bet : " + round.bet.get + " $\n"

case class StartState(round: Round) extends State :
    override def toString() = (1 to 5).map("[" + _.toString + "]\t\t").mkString 
        + "\n\n" +round.hand.get.map(_.toString + "\t").mkString

case class ReplaceState(round: Round) extends State:
    override def toString() : String = "\n\n" + round.hand.get.map(_.toString + "\t").mkString + "\n\n"
