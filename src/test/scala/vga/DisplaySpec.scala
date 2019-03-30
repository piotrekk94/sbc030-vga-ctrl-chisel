package vga

import org.scalatest.{FlatSpec, Matchers}

class DisplaySpec extends FlatSpec with Matchers{
  behavior of "DisplaySpec"

  it should "generate vga timings" in {
    chisel3.iotesters.Driver.execute(Array("--backend-name", "verilator"), () =>
      new DisplayController()) { dut =>
      new TimingGeneratorTest(dut)
    } should be(true)
  }
}
