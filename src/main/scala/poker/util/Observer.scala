package poker
package util

enum GameEvent:
  case INTRO,START,PLAY,EXIT

trait Observer: // View
  def update(e: GameEvent): Unit

trait Observable: // Controller
  var subscribers: Vector[Observer] = Vector()
  def add(s: Observer) = subscribers = subscribers :+ s
  def remove(s: Observer) = subscribers = subscribers.filterNot(o => o == s)
  def notifyObservers(e: GameEvent) = subscribers.foreach(o => o.update(e))
