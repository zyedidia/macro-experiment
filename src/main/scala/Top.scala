
import scala.language.experimental.macros
import mymacros.ChiselSubtypeOf

object Top extends App {
  def callOnChisel[A, B](a: A, b: B)(implicit subtype: ChiselSubtypeOf[A, B]): Unit = {
    println(s"$a is cool!")
  }
  callOnChisel(new Foo, new Bar)
}

class A {
  val a = 12
}

class Foo extends A {
  val x = "hi"
}

class Bar {
  val y = "bye"
}

