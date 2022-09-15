package fetch

import chisel3._
import chisel3.experimental.IO
import common.Consts._

class ImemPortIo extends Bundle {
  val addr = Input(UInt(WORD_LEN.W))
  val inst = Output(UInt(WORD_LEN.W))
}

class Top {
  val io = IO(new Bundle() {
    val exit = Output(Bool())
  })

  val core = Module(new Core())
  val memory = Module(new Memory())

  // CoreのioとMemoryのioはImemPortIoを反転したものなのでそのまま繋げる
  core.io.imem <> memory.io.imem

  io.exit := core.io.exit
}
