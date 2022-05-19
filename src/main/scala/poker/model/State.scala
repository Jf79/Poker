package poker
package model

import util.State
import util.Stateable


case class StartState(round: Round) extends State :
    var stateable = round
    var message = ""
    
    override def execute() : Option[_] = 
        message = "\nThe Round started.\nGood Luck.\n"
        stateable.handle(new RiskTypeEvent)
        Some(round)
    override def execute[T](arg: => T) : Option[T] = throw new UnsupportedOperationException
    override def execute[T, V](doThis: V => T, arg : V) : Option[T] = throw new UnsupportedOperationException
    override def toString = message

    
case class RiskTypeState(round: Round) extends State :
    var stateable  = round
    var message = ""
    override def execute() : Option[_] = throw new UnsupportedOperationException
    override def execute[T](arg: => T) : Option[T] = throw new UnsupportedOperationException
    override def execute[T, V](setRiskType: V => T, arg : V) : Option[T] = 
        if(RiskType(arg.toString).equals(new HighRisk) && round.player.money < 30)
            message = "\nYou dont have enough credit to play High Risk."
            stateable.handle(new RiskTypeEvent)
            throw new IllegalArgumentException()
        val temp: T = setRiskType(arg)
        message = "\nYou choose: " + round.riskType.get.message
        stateable.handle(new BetEvent)
        Some(temp)
    override def toString = message


case class BetState(round: Round) extends State :
    var stateable  = round
    var message = ""
    override def execute() : Option[_] = throw new UnsupportedOperationException
    override def execute[T](arg: => T) : Option[T] = throw new UnsupportedOperationException
    override def execute[T, V](setBet: V => T, arg : V) : Option[T] = 
        if(round.player.money < arg.asInstanceOf[Int])
            message = "\nYou dont have enough credit.\nPlease reduce your bet."
            stateable.handle(new BetEvent)
            throw new IllegalArgumentException
        stateable.handle(new DealCardsEvent)
        val temp : T = setBet(arg)
        message += "\nYour bet : " + round.bet.get + " $\n"
        Some(temp)

    override def toString = message


case class DealCardsState(round: Round) extends State:
    var stateable  = round
    override def execute() : Option[_] = throw new UnsupportedOperationException    
    override def execute[T](dealCards: => T) : Option[T] = 
        stateable.handle(new HoldCardsEvent)
        Some(dealCards)
    override def execute[T, V](doThis: V => T, arg : V) : Option[T] = throw new UnsupportedOperationException
    override def toString : String = 
        "\n\n" + (1 to 5).map("["+ _.toString + "]\t\t").mkString + 
        "\n" + round.hand.get.map(_.toString + "\t").mkString + "\n\n"

case class HoldCardsState(round: Round) extends State:
    var stateable  = round
    override def execute() : Option[_] = throw new UnsupportedOperationException    
    override def execute[T](arg: => T) : Option[T] = throw new UnsupportedOperationException
    override def execute[T, V](holdCards: V => T, arg : V) : Option[T] = 
        stateable.handle(new EndEvent)
        Some(holdCards(arg))
    override def toString : String = 
        "\n\n" + round.hand.get.map(_.toString + "\t").mkString + "\n\n"


case class EndState(round: Round) extends State:
    var stateable  = round
    override def execute() : Option[_] = None    
    override def execute[T](arg: => T) : Option[T] = None
    override def execute[T, V](doThis: V => T, arg : V) : Option[T] = None
    override def toString : String = "THE END"
