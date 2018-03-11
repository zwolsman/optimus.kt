package com.zwolsman.optimus

import java.lang.Math.pow
import java.math.BigInteger
import java.util.*

class Energon(prime: BigInteger?, private val size: Int = Optimus.DEFAULT_SIZE) {

    companion object {
        private fun generatePrime(size: Int = Optimus.DEFAULT_SIZE) = BigInteger.probablePrime(size, Random())

        private fun createMaxInt(size: Int) = BigInteger.valueOf(pow(2.0, size.toDouble()).toLong()).subtract(BigInteger.ONE)

        fun calculateInverse(prime: BigInteger, size: Int = Optimus.DEFAULT_SIZE): BigInteger {
            val max = createMaxInt(size) + BigInteger.ONE
            return prime.modInverse(max)
        }

        fun generateRandomInteger(size: Int = Optimus.DEFAULT_SIZE): BigInteger {
            val byteArray = ByteArray(4)
            Random().nextBytes(byteArray)
            return BigInteger(byteArray).and(createMaxInt(size))
        }

        fun generate(prime: BigInteger? = null, size: Int = Optimus.DEFAULT_SIZE): Array<BigInteger> {
            val instance = Energon(prime, size)
            return arrayOf(instance.prime, instance.inverse, instance.random)
        }
    }

    val prime: BigInteger
    val inverse: BigInteger
        get() = calculateInverse(prime, size)

    val random: BigInteger
        get() = generateRandomInteger(size)

    init {
        if (prime == null) {
            this.prime = generatePrime(size)
        } else {
            this.prime = prime
        }
    }
}