import com.zwolsman.optimus.Energon
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.junit.jupiter.api.Assertions.assertEquals
import java.lang.Math.pow
import java.math.BigInteger

class EnergonTest : Spek({

    val bitLengths = listOf(31, 32, 24, 16)

    bitLengths.forEach { bitLength ->

        given("a bit length of $bitLength") {
            it("generates a random set") {
                val set = Energon.generate(null, bitLength)
                assertEquals(3, set.size)
                assertEquals(BigInteger.ONE,
                        set[0].multiply(set[1])
                                .and(BigInteger.valueOf(pow(2.0, bitLength.toDouble()).toLong()) - BigInteger.ONE),
                        "Prime: ${set[0]}, Inverse: ${set[1]}, Xor: ${set[2]}, Bit length: $bitLength")
            }
        }
    }

    data class AskedSetTestData(val bitLength: Int, val prime: BigInteger, val expectedInverse: BigInteger)

    val askedSetTestData = listOf(
            AskedSetTestData(31, BigInteger.valueOf(1580030173), BigInteger.valueOf(59260789)),
            AskedSetTestData(32, BigInteger.valueOf(1580030173), BigInteger.valueOf(59260789)),
            AskedSetTestData(24, BigInteger.valueOf(12105601), BigInteger.valueOf(15698049)),
            AskedSetTestData(16, BigInteger.valueOf(1588507), BigInteger.valueOf(54547))
    )

    askedSetTestData.forEach { (bitLength, prime, expectedInverse) ->
        given("bit length = $bitLength and prime = $prime") {
            it("should have an inverse of $expectedInverse") {
                val set = Energon.generate(prime, bitLength)
                assertEquals(3, set.size)
                assertEquals(prime, set[0])
                assertEquals(expectedInverse, set[1])
            }
        }
    }
})