
package ammonite
package $file.src.main.scala.poker
import _root_.ammonite.interp.api.InterpBridge.{
  value => interp
}
import _root_.ammonite.interp.api.InterpBridge.value.{
  exit,
  scalaVersion
}
import _root_.ammonite.interp.api.IvyConstructor.{
  ArtifactIdExt,
  GroupIdExt
}
import _root_.ammonite.compiler.CompilerExtensions.{
  CompilerInterpAPIExtensions,
  CompilerReplAPIExtensions
}
import _root_.ammonite.runtime.tools.{
  browse,
  grep,
  time,
  tail
}
import _root_.ammonite.compiler.tools.{
  desugar,
  source
}
import _root_.mainargs.{
  arg,
  main
}
import _root_.ammonite.repl.tools.Util.{
  PathRead
}


object Worksheet{
/*<script>*/import scala.xml

val array = Array(10,20,30,40,50)
/*<amm>*/val res_2 = /*</amm>*/array.indexOf(5)
val out = new java.io.PrintStream(System.out, true, "UTF-8");
/*<amm>*/val res_4 = /*</amm>*/out.print("\u2660\uFE0F")
/*<amm>*/val res_5 = /*</amm>*/println("ko")
/*<amm>*/val res_6 = /*</amm>*/println(3 + 5)
val file = scala.xml.XML.loadFile("round.xml")
val bet = (file \ "bet").text.toInt
val hand = (file \ "hand")
/*

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
*//*</script>*/ /*<generated>*/
def $main() = { scala.Iterator[String]() }
  override def toString = "Worksheet"
  /*</generated>*/
}
