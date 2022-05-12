package poker
package util

trait Observer: // View
  def update: Unit

trait Observable: // Controller
  var subscribers: Vector[Observer] = Vector()
  def add(s: Observer) = subscribers = subscribers :+ s
  def remove(s: Observer) = subscribers = subscribers.filterNot(o => o == s)
  def notifyObservers = subscribers.foreach(o => o.update)
