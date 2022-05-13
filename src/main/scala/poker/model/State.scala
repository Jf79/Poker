package poker
package model

import util.Event
import util.State

case class StartState(round: Round) extends State :
    override def execute[T](arg : Option[T]) : Round = 
        round.setBet(arg.get.asInstanceOf[Int])
    
    override def toString = "\nYour credit: " + round.player.money + " $\n"

    
case class RiskTypeState(round: Round) extends State :
    override def execute[T](arg : Option[T]) : Round = 
        round.setBet(arg.get.asInstanceOf[Int])
    
    override def toString = "\nYour credit: " + round.player.money + " $\n"


case class BetState(round: Round) extends State :
    override def execute[T](arg : Option[T]) : Round = 
        round.setBet(arg.get.asInstanceOf[Int])

    override def toString = "\n" + round.riskType.get.message + "\nyour bet : " 
        + round.bet.get + " $\n"


case class ReplaceState(round: Round) extends State:
    override def execute[T](arg : Option[T]) : Round = 
        round.setBet(arg.get.asInstanceOf[Int])

    override def toString : String = 
        "\n\n" + round.hand.get.map(_.toString + "\t").mkString + "\n\n"
