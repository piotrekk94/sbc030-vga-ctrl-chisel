package vga

import chisel3._
import chisel3.util._

class TextGenerator extends Module{
  val io = IO(new Bundle{
    val timing = Flipped(new TimingBundle)

    val hsync = Output(Bool())
    val vsync = Output(Bool())

    val addr = Output(UInt(13.W))
    val data = Input(UInt(16.W))

    val red = Output(UInt(2.W))
    val green = Output(UInt(2.W))
    val blue = Output(UInt(2.W))
  })

  val colSelect = WireInit(io.timing.x(2, 0))
  val rowSelect = WireInit(io.timing.y(3, 0))

  val hsync = WireInit(io.timing.hsync)
  val vsync = WireInit(io.timing.vsync)
  val de = WireInit(io.timing.de)

  val charAddr = RegInit(0.U(11.W))
  val charAddrOld = RegInit(0.U(11.W))

  val charAttr = RegInit(0.U(8.W))
  val charAttrTmp = RegInit(0.U(8.W))
  val charBit = RegInit(0.U(8.W))

  val de_d = RegInit(false.B)
  val hsync_d = RegInit(false.B)
  val vsync_d = RegInit(false.B)

  val addr = RegInit(0.U(io.addr.getWidth.W))

  val colorData = RegInit(0.U(4.W))

  io.addr := addr

  io.hsync := hsync_d
  io.vsync := vsync_d

  io.red := Cat(colorData(0), colorData(3))
  io.green := Cat(colorData(1), colorData(3))
  io.blue := Cat(colorData(2), colorData(3))


  when(colSelect === 7.U){
    hsync_d := hsync
    vsync_d := vsync
    de_d := de
  }

  when(io.timing.de){
    when(colSelect === 0.U){
      addr := charAddr
      charAddr := charAddr + 1.U
    }

    when(colSelect === 3.U) {
      charAttrTmp := io.data(15, 8)
      addr := Cat(3.U(2.W), io.data(7, 0), rowSelect(3, 1))
    }

    when(colSelect === 7.U) {
      when(rowSelect(0) === false.B){
        charBit := io.data(15, 8)
      }.otherwise{
        charBit := io.data(7, 0)
      }
      charAttr := charAttrTmp
    }
  }

  when(io.timing.hsync){
    charBit := 0.U
    when(rowSelect === 15.U){
      charAddrOld := charAddr
    }.otherwise{
      charAddr := charAddrOld
    }
  }

  when(io.timing.vsync){
    charAddr := 0.U
    charAddrOld := 0.U
  }

  when(de_d){
    when(Reverse(charBit)(colSelect) === 0.U){
      colorData := charAttr(7, 4)
    }.otherwise{
      colorData := charAttr(3, 0)
    }
  }.otherwise{
    colorData := 0.U
  }
}
