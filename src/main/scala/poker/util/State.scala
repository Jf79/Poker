package poker
package util

import model.Round

trait Stateable:
    var state: State
    def handle(e: Event): State

trait State:
    def execute[T](arg : Option[T]) : Round
    def toString: String
