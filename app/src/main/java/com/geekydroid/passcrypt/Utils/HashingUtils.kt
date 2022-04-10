package com.geekydroid.passcrypt.Utils

import java.math.BigInteger
import java.security.SecureRandom
import java.text.DateFormat
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import kotlin.experimental.xor

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

    private fun hexToDecimal(hex: String): ByteArray {
        val bytes = ByteArray(hex.length / 2)
        for (i in bytes.indices) {
            bytes[i] = Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16).toByte()
        }

        return bytes
    }


    fun verifyPassword(userEnteredPassword: String, storedPassword: String): Boolean {
        val parts = storedPassword.split(":")

        val iterations = parts[0].toInt()
        val salt = hexToDecimal(parts[1])
        val hash = hexToDecimal(parts[2])

        val spec = PBEKeySpec(userEnteredPassword.toCharArray(), salt, iterations, hash.size * 8)
        val skf = SecretKeyFactory.getInstance(PBKDF2WithHmacSHA1)
        val testHash = skf.generateSecret(spec).encoded
        var diff: Int = hash.size.xor(testHash.size)
        var i = 0
        while (i < hash.size && i < testHash.size) {
            diff = diff.or(hash[i].xor(testHash[i]).toInt())
            i++
        }
        return diff == 0
    }

    fun getCurrentTimeInMs() = DateFormat.getDateTimeInstance().format(System.currentTimeMillis())
}