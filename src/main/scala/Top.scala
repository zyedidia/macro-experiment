
import scala.language.experimental.macros
import mymacros.Cool

object Top extends App {
  def callOnCool[A](a: A)(implicit cool: Cool[A]): Unit = {
    println(s"$a is cool!")
  }
  callOnCool(Foo(3))
  callOnCool(Bar("hi"))
}

case class Foo(x: Int)

case class Bar(x: String)

