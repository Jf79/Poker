import poker.model._

import poker.model.CardsObject._

val array = Array(10,20,30,40,50)
array.indexOf(5)


val card = new Card(Symbol.HEART, Picture.TWO, 2)
card.toString
println(new Card(Symbol.HEART, Picture.TWO, 2).equals(new Card(Symbol.HEART, Picture.TWO, 2)))
var numbers = new Array[Int](3)
numbers(0) = 111
numbers(1) = 111
numbers(2) = 111
println((1 to 5).map("["+ _.toString + "]\t\t").mkString)
numbers(0) = 111
numbers(1) = 222
numbers(2) = 333
val a = new Array[Int](0)
val deck = createCards()


numbers.foreach(n => println(n))
numbers = numbers.filter(!_.equals(333))
numbers.foreach(n => println(n))
