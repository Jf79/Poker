package poker
package model

import util.State
import util.Stateable


case class StartState(round: Round) extends State :
    var stateable  = round
    var message = ""

    override def execute() : Option[_] = 
        if(round.player.money < 0)
            stateable.handle(new EndEvent)
            message = "\nYou dont have enough credit to play\n"
        else
            stateable.handle(new RiskTypeEvent)
            message = "\nYour credit: " + round.player.money +  " $\n"
        Some(round)

    override def execute[T](arg: => T) : Option[T] = 
        None

    override def execute[T, V](arg: V => T) : Option[T] = 
        None
    
    override def toString = "\nYour credit: " + round.player.money + " $\n"

    
case class RiskTypeState(round: Round) extends State :
    var stateable  = round

    override def execute() : Option[_] =
        None

    override def execute[T](arg: => T) : Option[T] = 
        None

    override def execute[T, V](arg: V => T) : Option[T] = 
        None
    
    override def toString = "\nYour credit: " + round.player.money + " $\n"


case class BetState(round: Round) extends State :
    var stateable  = round

    override def execute() : Option[_] =
        None

    override def execute[T](arg: => T) : Option[T] = 
        None

    override def execute[T, V](arg: V => T) : Option[T] = 
        None

    override def toString = "\n" + round.riskType.get.message + "\nyour bet : " 
        + round.bet.get + " $\n"


case class HoldCardsState(round: Round) extends State:
    var stateable  = round
    override def execute() : Option[_] =
        None    

    override def execute[T](arg: => T) : Option[T] = 
        None

    override def execute[T, V](arg: V => T) : Option[T] = 
        None

    override def toString : String = 
        "\n\n" + round.hand.get.map(_.toString + "\t").mkString + "\n\n"
