package poker
package model
package round

import util.State
import util.Stateable
import util.Combination._

import scala.util.{Try, Success, Failure}


case class StartState(round: Round) extends State :
    var stateable = round
    var message = ""
    
    override def execute() : Option[_] = 
        message = "\nThe Round started.\nYour credit: " + round.player.getMoney() + " $\n"
        stateable.handle(new RiskTypeEvent)
        round.updateMessage = message
        Some(round)
    override def execute[T](arg: => T) : Option[T] = throw new UnsupportedOperationException
    override def execute[V](doThis: V => Try[RoundInterface], arg : V) : Option[RoundInterface] = throw new UnsupportedOperationException
    override def toString = "Start"

    
case class RiskTypeState(round: Round) extends State :
    var stateable  = round
    var message = ""
    override def execute() : Option[_] = throw new UnsupportedOperationException
    override def execute[T](arg: => T) : Option[T] = throw new UnsupportedOperationException
    override def execute[V](setRiskType: V => Try[RoundInterface], arg : V) : Option[RoundInterface] = 
        var returnValue: RoundInterface = round
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
    override def execute[V](setBet: V => Try[RoundInterface], arg : V) : Option[RoundInterface] = 
        var returnValue:RoundInterface = round
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
    override def execute[V](doThis: V => Try[RoundInterface], arg : V) : Option[RoundInterface] = throw new UnsupportedOperationException
    override def toString = "Deal"

case class HoldCardsState(round: Round) extends State:
    var stateable  = round
    var message = ""
    override def execute() : Option[_] = throw new UnsupportedOperationException    
    override def execute[T](arg: => T) : Option[T] = throw new UnsupportedOperationException
    override def execute[V](holdCards: V => Try[RoundInterface], arg : V) : Option[RoundInterface] = 
        val temp = holdCards(arg)
        message = "\n\n" + round.hand.get.map(_.toString + "\t").mkString + "\n\n"
        round.updateMessage = message
        stateable.handle(new EvaluationEvent)
        Some(temp.get)
   
    override def toString = "Hold"


case class EvaluationState(round: Round) extends State:
    var stateable  = round
    override def execute() : Option[_] = None    
    override def execute[T](evaluation: => T) : Option[T] = 
        val t = evaluation
        val r = t.asInstanceOf[Round]
        if(round.combination.get.equals(NOTHING))
            round.updateMessage = "\nYou didnt won anything.\nGood Luck next time.\n"
        else
            round.updateMessage = r.combination.get.toString + " !\nYou won " + r.outcome + " $\n"
        stateable.handle(new EndEvent)
        Some(t)

    override def execute[V](doThis: V => Try[RoundInterface], arg : V) : Option[RoundInterface] = None

    override def toString = "Evaluation"

case class EndState(round: Round) extends State:
    var stateable  = round
    override def execute() : Option[_] = throw new UnsupportedOperationException    
    override def execute[T](evaluation: => T) : Option[T] = throw new UnsupportedOperationException

    override def execute[V](doThis: V => Try[RoundInterface], arg : V) : Option[RoundInterface] = 
        throw new UnsupportedOperationException

    override def toString = "End"

object State :
    def apply (game: String, round : Round) = 
        game match {
            case "End" => new EndState(round)
            case "Evaluation" => new EvaluationState(round)
            case "Hold" => new HoldCardsState(round)
            case "Deal" => new DealCardsState(round)
            case "Bet" => new BetState(round)
            case "Risk" => new RiskTypeState(round)
            case "Start" => new StartState(round)
        }