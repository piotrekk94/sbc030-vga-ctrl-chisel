package vga

import chisel3.iotesters._

class TimingGeneratorTest(dut: DisplayController) extends PeekPokeTester(dut){

  step(1e6.toInt)

}
