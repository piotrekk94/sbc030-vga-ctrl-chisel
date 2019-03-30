package vga

import chisel3._

class TimingBundle extends Bundle{
  val x = Output(UInt(VideoTimings.widthH.W))
  val y = Output(UInt(VideoTimings.widthV.W))

  val de = Output(Bool())
  val hsync = Output(Bool())
  val vsync = Output(Bool())
}
