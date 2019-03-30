package vga

import chisel3._

class DisplayController extends Module{
  val io = IO(new Bundle{
    val mode = Input(UInt(2.W))

    val data = Input(UInt(16.W))
    val addr = Output(UInt(13.W))
    val oe = Output(Bool())
    val rw = Output(Bool())

    val hsync = Output(Bool())
    val vsync = Output(Bool())

    val red = Output(UInt(2.W))
    val green = Output(UInt(2.W))
    val blue = Output(UInt(2.W))
  })

  val gen = Module(new TimingGenerator)
  val text = Module(new TextGenerator)

  io.oe := false.B
  io.rw := true.B

  text.io.timing := gen.io.timing

  text.io.data := io.data
  io.addr := text.io.addr

  io.hsync := text.io.hsync
  io.vsync := text.io.vsync

  io.red := text.io.red
  io.green := text.io.green
  io.blue := text.io.blue

}
