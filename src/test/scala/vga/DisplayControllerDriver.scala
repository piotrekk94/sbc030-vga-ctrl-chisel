package vga

object DisplayControllerDriver extends App{
  chisel3.Driver.execute(args, () => new DisplayController)
}
