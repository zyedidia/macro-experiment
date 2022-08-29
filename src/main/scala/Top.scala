import chisel3._

import scala.language.experimental.macros
import mymacros.ChiselSubtypeOf

object Top extends App {
  def callOnChisel[A, B](a: A, b: B)(implicit subtype: ChiselSubtypeOf[A, B]): Unit = {
    println(s"$a is cool!")
  }

  callOnChisel(new A, new B) // ok
  callOnChisel(new A, new C) // ok
  callOnChisel(new A, new A) // ok
  callOnChisel(new A, new D) // error: D is not a Chisel subtype of A
  callOnChisel(new E, new A) // ok
}

class A extends Bundle {
  val x = UInt(3.W)
  val y = UInt(3.W)
  val z = UInt(3.W)
}

class B extends Bundle {
  val x = UInt(3.W)
  val y = UInt(3.W)
}

class C extends Bundle {
  val y = UInt(3.W)
  val z = UInt(3.W)
}

class D extends Bundle {
  val a = UInt(3.W)
  val y = UInt(3.W)
  val z = UInt(3.W)
}

class E extends A {
  val e = UInt(3.W)
}
