package poker
package util

import model.Round

trait Event

trait Stateable:
    var state: State
    def handle(e: Event): State

trait State:
    var stateable: Stateable
    def execute() : Option[_]
    def execute[T](arg: => T) : Option[T]
    def execute[T, V](arg : V => T) : Option[T]
    def toString: String
