package poker
package aview

import util.Observer

trait UserInterface extends Observer:
  def run: Unit

