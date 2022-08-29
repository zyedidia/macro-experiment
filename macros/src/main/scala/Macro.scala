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

    def subtypeOf(a: Type, b: Type): Boolean = {
      if (a == NoType) {
        return false
      }

      val va = a.members
      val vb = b.members

      for (bval <- vb) {
        if (bval.isPublic) {
          if (bval.isTerm && bval.asTerm.isGetter) {
            val aval = a.member(TermName(bval.name.toString()))
            if (!subtypeOf(aval.info, bval.info)) {
              return false
            }
          } else if (!bval.isTerm) {
            val aval = a.member(TypeName(bval.name.toString()))
            if (aval.info != bval.info) {
              return false
            }
          }
        }
      }
      return true
    }

    val ta = implicitly[c.WeakTypeTag[A]]
    val tb = implicitly[c.WeakTypeTag[B]]
    val empty = q""

    if (!subtypeOf(ta.tpe, tb.tpe)) {
      c.error(empty.pos, s"${tb.tpe} is not a Chisel subset type of ${ta.tpe}")
    }

    return empty
  }
  implicit def genChisel[A, B]: ChiselSubtypeOf[A, B] = macro ChiselSubtypeOf.genChiselSubtypeOf[A, B]
}
