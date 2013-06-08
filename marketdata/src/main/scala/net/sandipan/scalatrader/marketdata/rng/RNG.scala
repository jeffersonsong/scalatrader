package net.sandipan.scalatrader.marketdata.rng

/**
 * A Pseudo-random number generator (i.e. deterministic).
 *
 * Credit:
 * https://github.com/pchiusano/fpinscala/blob/master/answers/src/main/scala/fpinscala/state/State.scala
 */
trait RNG {
  def nextInt: (Int, RNG)
}

object RNG {

  def simple(seed: Long): RNG = new RNG {
    def nextInt = {
      val seed2 = (seed*0x5DEECE66DL + 0xBL) & // `&` is bitwise AND
        ((1L << 48) - 1) // `<<` is left binary shift
      ((seed2 >>> 16).asInstanceOf[Int], // `>>>` is right binary shift with zero fill
        simple(seed2))
    }
  }

  def double(rng: RNG): (Double, RNG) = {
    val (nextValue, nextState) = rng.nextInt
    Math.abs(nextValue / Int.MaxValue.toDouble + 1) -> nextState
  }

  def positiveInt(rng: RNG): (Int, RNG) = {
    val (nextValue, nextState) = rng.nextInt
    Math.abs(nextValue) -> nextState
  }
}

