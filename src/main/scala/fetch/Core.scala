package fetch

import chisel3._
import chisel3.experimental.IO
import common.Consts._

class Core {
  // ImemPortIoを反転したもの
  // 出力addr 入力inst
  val io = IO(new Bundle() {
    val imem = Flipped(new ImemPortIo())
    val exit = Output(Bool())
  })

  // 32bit*32本のレジスタ
  val regfile = Mem(32, UInt(WORD_LEN.W))

  //********************************
  /* Instruction Fetch (IF) Stage */
  //********************************

  // プログラムカウンタ(初期値0) 1サイクル4カウントアップ
  val pc_reg = RegInit(START_ADDR)
  pc_reg := pc_reg + 4.U(WORD_LEN.W)

  io.imem.addr := pc_reg
  val inst = io.imem.inst

  // exit
  io.exit := (inst === 0x34333231.U(WORD_LEN.W))
}
