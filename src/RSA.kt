/**
 * Created by Nilo Otaviano on 24/10/2015.
 * Implementação do algoritmo de criptografia RSA
 */
import java.math.BigInteger

fun isPrime(number: Int): Boolean {
    val sqrt: Int = (Math.sqrt(number.toDouble()) + 1).toInt()

    for (i in 2..sqrt)
        if ((number % i) == 0)
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

class RSA(val p: Int, val q: Int) {
    constructor() : this(randomPrime(1, 255), randomPrime(1, 255))

    public val n: Int = p * q;
    public val e: Int;    // Public key
    public val d: Int;    // Private key

    init {
        val totient: Int = (p - 1) * (q - 1)
        e = randomPrime(2, totient - 1)
        d = BigInteger.valueOf(e.toLong())
                .modInverse(BigInteger.valueOf(totient.toLong())).toInt()
    }

    fun encrypt(msg: String): String {
        val encryptedMsg:MutableList<Char> = linkedListOf()

        for (b in msg) {
            // c = m^e (mod n)
            val encryptedB = BigInteger.valueOf(b.toLong())
                    .modPow(BigInteger.valueOf(e.toLong()), BigInteger.valueOf(n.toLong()))
            encryptedMsg.add(encryptedB.toInt().toChar())
        }
        return String(encryptedMsg.toCharArray())
    }

    fun decrypt(msg: String): String {
        val decryptedMsg:MutableList<Char> = linkedListOf()

        for (b in msg) {
            // c = m^d (mod n)
            val decryptedB = BigInteger.valueOf(b.toLong())
                    .modPow(BigInteger.valueOf(d.toLong()), BigInteger.valueOf(n.toLong()))
            decryptedMsg.add(decryptedB.toInt().toChar())
        }
        return String(decryptedMsg.toCharArray())
    }
}