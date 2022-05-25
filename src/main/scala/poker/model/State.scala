package poker
package model

import util.State
import util.Stateable
import scala.util.{Try, Success, Failure}


case class StartState(round: Round) extends State :
    var stateable = round
    var message = ""
    
    override def execute() : Option[_] = 
        message = "\nThe Round started.\nYour credit: " + round.player.money + " $\n"
        stateable.handle(new RiskTypeEvent)
        round.updateMessage = message
        Some(round)
    override def execute[T](arg: => T) : Option[T] = throw new UnsupportedOperationException
    override def execute[V](doThis: V => Try[Round], arg : V) : Option[Round] = throw new UnsupportedOperationException
    override def toString = "Start"

    
case class RiskTypeState(round: Round) extends State :
    var stateable  = round
    var message = ""
    override def execute() : Option[_] = throw new UnsupportedOperationException
    override def execute[T](arg: => T) : Option[T] = throw new UnsupportedOperationException
    override def execute[V](setRiskType: V => Try[Round], arg : V) : Option[Round] = 
        var returnValue = round
        val riskTypeTry = setRiskType(arg)
        if (riskTypeTry.isSuccess)
            riskTypeTry.get.updateMessage = "\nYou choose: " + round.riskType.get.message + "\n"
            stateable.handle(new BetEvent)
            returnValue = riskTypeTry.get
        else
            returnValue.updateMessage = riskTypeTry.failed.get.getMessage
        Some(returnValue)
    override def toString = "Risk"

case class BetState(round: Round) extends State :
    var stateable  = round
    var message = ""
    override def execute() : Option[_] = throw new UnsupportedOperationException
    override def execute[T](arg: => T) : Option[T] = throw new UnsupportedOperationException
    override def execute[V](setBet: V => Try[Round], arg : V) : Option[Round] = 
        var returnValue = round
        val betTry = setBet(arg)
        if (betTry.isSuccess)
            betTry.get.updateMessage = "\nYour bet : " + round.bet.get + " $\n"
            stateable.handle(new DealCardsEvent)
            returnValue = betTry.get
        else
            round.updateMessage = betTry.failed.get.getMessage
        Some(returnValue)
    override def toString = "Bet"

case class DealCardsState(round: Round) extends State:
    var stateable  = round
    var message = ""
    override def execute() : Option[_] = throw new UnsupportedOperationException    
    override def execute[T](dealCards: => T) : Option[T] = 
        stateable.handle(new HoldCardsEvent)
        val temp: Option[T] = Some(dealCards)
        message = "\n\n" + (1 to 5).map("["+ _.toString + "]\t\t").mkString
        message += "\n\n" + round.hand.get.map(_.toString + "\t").mkString + "\n\n"
        round.updateMessage = message
        temp
    override def execute[V](doThis: V => Try[Round], arg : V) : Option[Round] = throw new UnsupportedOperationException
    override def toString = "Deal"

case class HoldCardsState(round: Round) extends State:
    var stateable  = round
    var message = ""
    override def execute() : Option[_] = throw new UnsupportedOperationException    
    override def execute[T](arg: => T) : Option[T] = throw new UnsupportedOperationException
    override def execute[V](holdCards: V => Try[Round], arg : V) : Option[Round] = 
        val temp = holdCards(arg)
        message = "\n\n" + round.hand.get.map(_.toString + "\t").mkString + "\n\n"
        round.updateMessage = message
        stateable.handle(new EndEvent)
        Some(temp.get)
   
    override def toString = "Hold"


case class EndState(round: Round) extends State:
    var stateable  = round
    override def execute() : Option[_] = None    
    override def execute[T](arg: => T) : Option[T] = None
    override def execute[V](doThis: V => Try[Round], arg : V) : Option[Round] = None

    override def toString = "End"

object State :
    def apply (game: String, round : Round) = 
        game match {
            case "End" => new EndState(round)
            case "Hold" => new HoldCardsState(round)
            case "Deal" => new DealCardsState(round)
            case "Bet" => new BetState(round)
            case "Risk" => new RiskTypeState(round)
            case "Start" => new StartState(round)
        }