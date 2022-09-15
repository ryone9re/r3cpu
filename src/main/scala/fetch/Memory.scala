package fetch

import chisel3._
import chisel3.experimental.IO
import chisel3.util.experimental.loadMemoryFromFile
import common.Consts._

class Memory {
  val io = IO(new Bundle() {
    val imem = new ImemPortIo()
  })

  // メモリとして8bit*16384本(16KB)のレジスタ
  val mem = Mem(16384, UInt(8.W))

  // メモリデータロード
  loadMemoryFromFile(mem, "src/hex/fetch.hex")

  // 各アドレスに格納されている8bitデータを4つつなげて32bitデータに
  io.imem.inst := Cat(
    mem(io.imem.addr + 3.U(WORD_LEN.W)),
    mem(io.imem.addr + 2.U(WORD_LEN.W)),
    mem(io.imem.addr + 1.U(WORD_LEN.W)),
    mem(io.imem.addr)
  )
}
