package com.example.data

import java.security.MessageDigest
import java.security.SecureRandom
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import android.util.Base64

/**
 * Salted PBKDF2 hashing for the passwords kept in the local database, so a copy of the phone's
 * data never reveals real passwords. Stored format: pbkdf2$iterations$salt$hash.
 * Older plain-text entries are still recognised once and upgraded on the next successful login.
 */
object PasswordHasher {
    private const val PREFIX = "pbkdf2$"
    private const val ITERATIONS = 60_000

    fun isHashed(stored: String) = stored.startsWith(PREFIX)

    fun hash(password: String): String {
        val salt = ByteArray(16).also { SecureRandom().nextBytes(it) }
        return PREFIX + ITERATIONS + "$" + b64(salt) + "$" + b64(derive(password, salt, ITERATIONS))
    }

    fun matches(stored: String, password: String): Boolean {
        if (!isHashed(stored)) return stored.isNotEmpty() && constantTimeEquals(stored.toByteArray(), password.toByteArray())
        val parts = stored.split("$")
        if (parts.size != 4) return false
        val iterations = parts[1].toIntOrNull() ?: return false
        val salt = Base64.decode(parts[2], Base64.NO_WRAP)
        val expected = Base64.decode(parts[3], Base64.NO_WRAP)
        return constantTimeEquals(expected, derive(password, salt, iterations))
    }

    private fun derive(password: String, salt: ByteArray, iterations: Int): ByteArray =
        SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
            .generateSecret(PBEKeySpec(password.toCharArray(), salt, iterations, 256)).encoded

    private fun b64(b: ByteArray) = Base64.encodeToString(b, Base64.NO_WRAP)
    private fun constantTimeEquals(a: ByteArray, b: ByteArray) = MessageDigest.isEqual(a, b)
}
