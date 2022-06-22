package poker
package model
package round

import util.Event

case class StartEvent() extends Event
case class RiskTypeEvent() extends Event
case class BetEvent() extends Event
case class DealCardsEvent() extends Event
case class HoldCardsEvent() extends Event
case class EvaluationEvent() extends Event
case class EndEvent() extends Event