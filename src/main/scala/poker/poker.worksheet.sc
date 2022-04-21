
class cards {
  val signs = Array("Herz", "Karo", "Pick", "Kreuz")
  val values = Array("7", "8", "9", "10", "B", "D", "K", "A")
}
1 + 2
val c = cards()
c.signs
c.values
c.values(1)

val card = new model.Card(model.Symbol.HEART, model.Picture.TWO, 2)
card.toString
var numbers = new Array[Int](3)
numbers(0) = 111
numbers(1) = 111
numbers(2) = 111
println(numbers.forall(c => c.equals(1117)))


numbers(0) = 111
numbers(1) = 222
numbers(2) = 333

val deck = model.Game.createCards()
deck(12)
val tui = new view.TUI()
tui.printNumbers()

numbers.foreach(n => println(n))
numbers = numbers.filter(!_.equals(333))
numbers.foreach(n => println(n))
