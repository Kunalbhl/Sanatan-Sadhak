package com.example.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File

/**
 * Keeps the profile photo permanently.
 * A picked gallery Uri loses its permission after restart, so the photo is copied into app storage
 * (resized to 512px). A small 256px JPEG (base64) is also kept for cloud backup in Firestore.
 */
object ProfileImageStore {

    private fun file(context: Context, email: String) =
        File(context.filesDir, "profile_${email.lowercase().replace(Regex("[^a-z0-9]"), "_")}.jpg")

    /** Copy a picked image into app storage; returns a stable file:// uri string ("" on failure). */
    fun importPicked(context: Context, picked: Uri, email: String): String = try {
        val bmp = context.contentResolver.openInputStream(picked)?.use { BitmapFactory.decodeStream(it) }
            ?: return ""
        val out = file(context, email)
        out.outputStream().use { scale(bmp, 512).compress(Bitmap.CompressFormat.JPEG, 88, it) }
        Uri.fromFile(out).toString() + "?v=" + System.currentTimeMillis()
    } catch (e: Exception) { "" }

    /** Small base64 copy of the saved photo for Firestore (≈20–40 KB). */
    fun toBase64(context: Context, email: String): String = try {
        val f = file(context, email)
        if (!f.exists()) "" else {
            val bmp = BitmapFactory.decodeFile(f.absolutePath)
            val bos = ByteArrayOutputStream()
            scale(bmp, 256).compress(Bitmap.CompressFormat.JPEG, 80, bos)
            Base64.encodeToString(bos.toByteArray(), Base64.NO_WRAP)
        }
    } catch (e: Exception) { "" }

    /** Restore a photo from its Firestore base64 copy; returns the local uri string. */
    fun fromBase64(context: Context, email: String, b64: String): String = try {
        if (b64.isBlank()) "" else {
            val f = file(context, email)
            f.writeBytes(Base64.decode(b64, Base64.NO_WRAP))
            Uri.fromFile(f).toString()
        }
    } catch (e: Exception) { "" }

    private fun scale(b: Bitmap, max: Int): Bitmap {
        val r = max.toFloat() / maxOf(b.width, b.height)
        return if (r >= 1f) b else Bitmap.createScaledBitmap(b, (b.width * r).toInt(), (b.height * r).toInt(), true)
    }
}
