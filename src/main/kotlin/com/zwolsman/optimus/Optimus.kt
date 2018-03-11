package com.zwolsman.optimus

import java.math.BigInteger

class Optimus(private val prime: BigInteger, private val inverse: BigInteger, private val xor: Int = 0, size: Int = DEFAULT_SIZE) {

    private val max: Int = (Math.pow(2.0, size.toDouble()) - 1).toInt()

    companion object {
        const val DEFAULT_SIZE = 32
    }

    fun encode(value: Int) = ((value * prime.toInt()) and max) xor xor
    fun decode(value: Int) = ((value xor xor) * inverse.toInt()) and max

}