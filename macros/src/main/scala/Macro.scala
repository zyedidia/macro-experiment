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

trait ChiselSubtypeOf[A, B]

object ChiselSubtypeOf {
  def genChiselSubtypeOf[A : c.WeakTypeTag, B : c.WeakTypeTag](c: Context): c.Tree = {
    import c.universe._
    val ta = implicitly[c.WeakTypeTag[A]]
    val tb = implicitly[c.WeakTypeTag[B]]
    val empty = q""

    val va = ta.tpe.members
    val vb = tb.tpe.members

    vb.foreach(bval => {
      if (bval.asTerm.isGetter) {
        val aval = ta.tpe.member(TermName(bval.name.toString()))
        if (aval.info != bval.info) {
          c.error(empty.pos, s"${tb.tpe} is not a Chisel subset type of ${ta.tpe} (mismatch at ${tb.tpe}.${bval.name})")
        }
      }
    })

    return empty
  }
  implicit def genChisel[A, B]: ChiselSubtypeOf[A, B] = macro ChiselSubtypeOf.genChiselSubtypeOf[A, B]
}
