/**
 * Created by Nilo Otaviano on 24/10/2015.
 * RSA Algorithm implementation
 */
import java.math.BigInteger

val min = 2;
val max = 255;

fun isPrime(number: Int): Boolean {
    val sqrt: Int = (Math.sqrt(number.toDouble()) + 1).toInt()

    for (i in 2..sqrt)
        if (number != i && (number % i) == 0)
            return false
    return true
}

tailrec fun randomPrime(min: Int, max: Int): Int {
    val p: Int = (Math.random() * (max - min) + min).toInt()

    return if (isPrime(p))
        p
    else
        randomPrime(min, max)
}

class RSA(val p: Int, var q: Int) {
    constructor() : this(randomPrime(min, max), randomPrime(min, max))

    public val n: Int = p * q;
    public val encryptionKey: Int;    // Public key
    public val decryptionKey: Int;    // Private key

    init {
        while (p == q)
            q = randomPrime(min, max)

        val totient: Int = (p - 1) * (q - 1)
        var totientCoprime: Int
        var totientCoprimeModInverse: Int

        do {
            totientCoprime = randomPrime(2, totient - 1)

            // Check if totientCoprime is actually coprime to totient
            // Get a net random prime otherwise
            while (BigInteger.valueOf(totientCoprime.toLong()).gcd(BigInteger.valueOf(totient.toLong())).toInt() != 1)
                totientCoprime = randomPrime(2, totient - 1)

            totientCoprimeModInverse = BigInteger.valueOf(totientCoprime.toLong())
                    .modInverse(BigInteger.valueOf(totient.toLong())).toInt()
        } while (totientCoprime == totientCoprimeModInverse)

        encryptionKey = totientCoprime
        decryptionKey = totientCoprimeModInverse
    }

    fun encrypt(msg: String): String {
        val encryptedMsg: MutableList<Char> = linkedListOf()

        for (b in msg) {
            // c = m^e (mod n)
            val encryptedB = BigInteger.valueOf(b.toLong())
                    .modPow(BigInteger.valueOf(encryptionKey.toLong()), BigInteger.valueOf(n.toLong()))
            encryptedMsg.add(encryptedB.toInt().toChar())
        }
        return String(encryptedMsg.toCharArray())
    }

    fun decrypt(msg: String): String {
        val decryptedMsg: MutableList<Char> = linkedListOf()

        for (b in msg) {
            // c = m^d (mod n)
            val decryptedB = BigInteger.valueOf(b.toLong())
                    .modPow(BigInteger.valueOf(decryptionKey.toLong()), BigInteger.valueOf(n.toLong()))
            decryptedMsg.add(decryptedB.toInt().toChar())
        }
        return String(decryptedMsg.toCharArray())
    }

    override fun toString(): String {
        return "\tp: $p\n"
                .concat("\tq: $q\n")
                .concat("\tn: $n\n")
                .concat("\te: $encryptionKey\n")
                .concat("\td: $decryptionKey\n")
    }
}