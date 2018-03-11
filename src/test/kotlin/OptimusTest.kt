import com.zwolsman.optimus.Energon
import com.zwolsman.optimus.Optimus
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import java.math.BigInteger

class OptimusTest : Spek({
    given("an optimus instance") {
        val bl31 = 31
        val bl32 = 32
        val bl24 = 24
        val bls = listOf(bl31, bl32, bl24)

        val randomXor = 873691988
        val smlPrime = BigInteger.valueOf(10000019)

        val lrgPrimes = mapOf<Int, BigInteger>(
                bl31 to BigInteger.valueOf(2147483647),
                bl32 to BigInteger.valueOf(4294967291),
                bl24 to BigInteger.valueOf(999999967)
        )

        val smlPrimeInverses = mapOf(
                bl31 to Energon.calculateInverse(smlPrime, bl31),
                bl32 to Energon.calculateInverse(smlPrime, bl32),
                bl24 to Energon.calculateInverse(smlPrime, bl24)
        )

        val lrgPrimeInverses = mapOf(
                bl31 to Energon.calculateInverse(lrgPrimes[bl31]!!, bl31),
                bl32 to Energon.calculateInverse(lrgPrimes[bl32]!!, bl32),
                bl24 to Energon.calculateInverse(lrgPrimes[bl24]!!, bl24)
        )

        data class TestData(val prime: BigInteger, val inverse: BigInteger, val xor: Int, val bitLength: Int, val value: Int)

        bls.flatMap {
            listOf(
                    TestData(smlPrime, smlPrimeInverses[it]!!, 0, it, 1),
                    TestData(smlPrime, smlPrimeInverses[it]!!, 0, it, it),
                    TestData(smlPrime, smlPrimeInverses[it]!!, randomXor, it, 1),
                    TestData(smlPrime, smlPrimeInverses[it]!!, randomXor, it, it),
                    TestData(lrgPrimes[it]!!, lrgPrimeInverses[it]!!, 0, it, 1),
                    TestData(lrgPrimes[it]!!, lrgPrimeInverses[it]!!, 0, it, it),
                    TestData(lrgPrimes[it]!!, lrgPrimeInverses[it]!!, randomXor, it, 1),
                    TestData(lrgPrimes[it]!!, lrgPrimeInverses[it]!!, randomXor, it, it)
            )
        }.forEach { (prime: BigInteger, inverse: BigInteger, xor: Int, bitLength: Int, value: Int) ->
            it("should encode and decode") {
                val optimus = Optimus(prime, inverse, xor, bitLength)
                val encoded = optimus.encode(value)
                val decoded = optimus.decode(encoded)
                val assertMsgDetails = "Prime: $prime, Inverse: $inverse, Xor: $xor, Bit length: $bitLength, Value: $value"

                assertNotEquals(value, encoded, assertMsgDetails)
                assertNotEquals(encoded, decoded, assertMsgDetails)
                assertEquals(value, decoded, assertMsgDetails)
            }
        }
    }
})