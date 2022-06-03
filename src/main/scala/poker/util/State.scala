package poker
package util

import model.round.RoundInterface

import scala.util.Try

trait Event

trait Stateable:
    var state: State
    def handle(e: Event): State

trait State:
    var stateable: Stateable
    def execute() : Option[_]
    def execute[T](arg: => T) : Option[T]
    def execute[V](doThis: V => Try[RoundInterface], arg : V) : Option[RoundInterface]
    def toString: String
