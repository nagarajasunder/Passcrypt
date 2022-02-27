package com.geekydroid.passcrypt.Utils

import java.math.BigInteger
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

private const val SHA1PRNG = "SHA1PRNG"
private const val PBKDF2WithHmacSHA1 = "PBKDF2WithHmacSHA1"
private const val HASH_ITERATIONS = 1000

object HashingUtils {


    private fun getSalt(): ByteArray {

        val secureRandom = SecureRandom.getInstance(SHA1PRNG)
        val salt = ByteArray(16)
        secureRandom.nextBytes(salt)
        return salt
    }

    fun getStrongPasswordHash(password: String): String {
        val salt = getSalt()
        val spec = PBEKeySpec(password.toCharArray(), salt, HASH_ITERATIONS, 64 * 8)
        val skf = SecretKeyFactory.getInstance(PBKDF2WithHmacSHA1)
        val hash = skf.generateSecret(spec).encoded

        return "$HASH_ITERATIONS:${decimalToHex(salt)}:${decimalToHex(hash)}"

    }


    private fun decimalToHex(bytearr: ByteArray): String {
        val bi = BigInteger(1, bytearr)
        val hex = bi.toString(16)
        val paddingLength = (bytearr.size * 2) - hex.length
        return if (paddingLength > 0) {
            String.format("%0${paddingLength}d", 0) + hex
        } else {
            hex
        }
    }

}