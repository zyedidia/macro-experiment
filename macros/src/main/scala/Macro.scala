package mymacros

import scala.language.experimental.macros
import scala.reflect.macros.blackbox.Context

trait Cool[A]

object Cool {
  def genCoolImpl[A : c.WeakTypeTag](c: Context): c.Tree = {
    import c.universe._
    val tt = implicitly[c.WeakTypeTag[A]]
    val x = q""
    if (tt.tpe.toString() == "Foo") {
      return x
    }
    c.error(x.pos, "Not a Foo, you Fool!!")
    return x
  }
  implicit def genCool[A]: Cool[A] = macro Cool.genCoolImpl[A]
}
