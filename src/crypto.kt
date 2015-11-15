/**
 * Created by Nilo Otaviano on 24/10/2015.
 * Main
 */
fun main(args: Array<String>)
{
    val rsa = RSA()

    print("Your message here: ")
    val msg = readLine()

    if(msg != null) {
        val encryptedMessage = rsa.encrypt(msg)
        val decryptedMessage = rsa.decrypt(encryptedMessage)

        assert(msg.equals(encryptedMessage))

        println("Encrypted message: $encryptedMessage")
        println("Decrypted message: $decryptedMessage")
    }
}