package vga

import chisel3._

class TimingGenerator extends Module{
  val io = IO(new Bundle{
    val timing = new TimingBundle
  })

  val cntH = RegInit(0.U(VideoTimings.widthH.W))
  val cntV = RegInit(0.U(VideoTimings.widthV.W))

  io.timing.de := (cntH < VideoTimings.visH.U) && (cntV < VideoTimings.visV.U)

  io.timing.hsync := ((cntH >= (VideoTimings.visH + VideoTimings.fpH).U) &&
              (cntH < (VideoTimings.visH + VideoTimings.fpH + VideoTimings.spH).U))
  io.timing.vsync := ((cntV >= (VideoTimings.visV + VideoTimings.fpV).U) &&
              (cntV < (VideoTimings.visV + VideoTimings.fpV + VideoTimings.spV).U))

  io.timing.x := cntH
  io.timing.y := cntV

  when(cntH === VideoTimings.maxH.U){
    cntH := 0.U
    when(cntV === VideoTimings.maxV.U){
      cntV := 0.U
    }.otherwise{
      cntV := cntV + 1.U
    }
  }.otherwise{
    cntH := cntH + 1.U
  }
}
