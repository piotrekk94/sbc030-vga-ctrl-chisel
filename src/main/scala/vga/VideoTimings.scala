package vga

import chisel3.util.log2Ceil

object VideoTimings {
  val visH : Int = 640
  val fpH : Int = 16
  val spH : Int = 96
  val bpH : Int = 48

  val maxH : Int = visH + fpH + spH + bpH - 1
  val widthH : Int = log2Ceil(maxH)

  val visV : Int = 400
  val fpV : Int = 12
  val spV : Int = 2
  val bpV : Int = 35

  val maxV : Int = visV + fpV + spV + bpV - 1
  val widthV : Int = log2Ceil(maxV)
}
