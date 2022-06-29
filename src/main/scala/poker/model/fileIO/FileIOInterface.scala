package poker
package model
package fileIO

import round.RoundInterface

trait FileIOInterface:
    def save(round: RoundInterface, filename: String): Unit
    def load: RoundInterface