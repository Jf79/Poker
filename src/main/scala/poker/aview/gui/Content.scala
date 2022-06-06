package poker
package aview
package gui

import java.awt.Point
import java.awt.Color
import java.awt.Color._
import java.awt.event.ActionListener
import java.awt.Graphics2D
import java.awt.Font
import java.awt.BasicStroke
import controller.controller.ControllerInterface

abstract class MyContent():
    var visible: Boolean = false
    val height: Int = bottomR.y - topR.y
    val width: Int =  topR.x - topL.x
    val topL: Point
    val topR: Point 
    val bottomR: Point 
    val bottomL: Point 
    val color: Color

abstract class MyButton() extends MyContent:
    def clicked(c: ControllerInterface): Unit





