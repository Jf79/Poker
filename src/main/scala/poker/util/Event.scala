package poker
package util

trait Event

case class BetEvent() extends Event
case class StartEvent() extends Event
case class ReplaceEvent() extends Event
case class EndEvent() extends Event
