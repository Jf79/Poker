package poker
package model

import util.Event

case class StartEvent() extends Event
case class RiskTypeEvent() extends Event
case class BetEvent() extends Event
case class DealCardsEvent() extends Event
case class HoldCardsEvent() extends Event
case class EvaluationEvent() extends Event