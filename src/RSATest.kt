import kotlin.test.assertFalse

class RSATest {

    @org.junit.Test
    fun testIsPrime() {
        assert(isPrime(3))
        assert(isPrime(5))
        assert(isPrime(7))
        assert(isPrime(277))
        assert(isPrime(503))
        assert(isPrime(653))
        assert(isPrime(653))
        assert(isPrime(997))
        assertFalse(isPrime(4))
        assertFalse(isPrime(16))
        assertFalse(isPrime(50))
        assertFalse(isPrime(100))
    }

    @org.junit.Test
    fun testRandomPrime() {
        val min = 1
        var max = 255
        var prime: Int

        for (i in 1..256) {
            prime = randomPrime(min, max)
            assert(isPrime(prime))
            assert(prime >= min && prime <= max)
        }

        max = 4
        for (i in 1..128) {
            prime = randomPrime(min, max)
            assert(isPrime(prime))
            assert(prime >= min && prime <= max)
        }
    }

    @org.junit.Test
    fun testEncrypt() {
        var rsa = RSA(61, 53)
        val msg = "Testing RSA algorithm"

        for (i in 1..1000) {
            val encryptedMessage = rsa.encrypt(msg)
            assert(!msg.equals(encryptedMessage)) { "i: $i\nrsa:\n${rsa.toString()}" }
            rsa = RSA(61, 53)
        }
    }

    @org.junit.Test
    fun testDecrypt() {
        var rsa = RSA(61, 53)
        val msg = "Testing RSA algorithm"

        for (i in 1..1000) {
            val encryptedMessage = rsa.encrypt(msg)
            val decryptedMessage = rsa.decrypt(encryptedMessage)
            assert(msg.equals(decryptedMessage)) { "i: $i, decrypted message: $decryptedMessage, \n rsa:\n${rsa.toString()}" }
            rsa = RSA(61, 53)
        }
    }
}