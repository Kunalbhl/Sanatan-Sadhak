package com.example.utils

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.content.ContextCompat

enum class AppPermissionStatus {
    GRANTED,
    DENIED,
    PERMANENTLY_DENIED
}

/**
 * Centralized PermissionCoordinator for Sanatan Sadhak.
 * Real Android OS grant state via ContextCompat.checkSelfPermission is ALWAYS the source of truth.
 * Ensures permissions already granted are NEVER requested again.
 * Device-level permission grants are safely shared across accounts without leaking data.
 */
object PermissionManager {

    /**
     * Checks actual Android POST_NOTIFICATIONS grant status.
     * Always returns GRANTED on pre-Android 13.
     */
    fun getNotificationPermissionStatus(context: Context): AppPermissionStatus {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            return AppPermissionStatus.GRANTED
        }
        val isGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED

        if (isGranted) {
            return AppPermissionStatus.GRANTED
        }

        val prefs = context.getSharedPreferences("sadhak_permissions", Context.MODE_PRIVATE)
        val userDeclined = prefs.getBoolean("declined_POST_NOTIFICATIONS", false)

        return if (userDeclined) {
            AppPermissionStatus.PERMANENTLY_DENIED
        } else {
            AppPermissionStatus.DENIED
        }
    }

    /**
     * Checks actual Android Location grant status (Fine or Coarse).
     */
    fun getLocationPermissionStatus(context: Context): AppPermissionStatus {
        val fineGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val coarseGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (fineGranted || coarseGranted) {
            return AppPermissionStatus.GRANTED
        }

        val prefs = context.getSharedPreferences("sadhak_permissions", Context.MODE_PRIVATE)
        val userDeclined = prefs.getBoolean("declined_LOCATION", false)

        return if (userDeclined) {
            AppPermissionStatus.PERMANENTLY_DENIED
        } else {
            AppPermissionStatus.DENIED
        }
    }

    fun markPermissionRequested(context: Context, permissionKey: String) {
        val prefs = context.getSharedPreferences("sadhak_permissions", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("requested_$permissionKey", true).apply()
    }

    fun markPermissionDeclined(context: Context, permissionKey: String) {
        val prefs = context.getSharedPreferences("sadhak_permissions", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("declined_$permissionKey", true).apply()
    }

    /**
     * Safely routes user to Android App Settings when a permission is permanently denied.
     */
    fun openAppSettings(context: Context) {
        try {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                data = Uri.fromParts("package", context.packageName, null)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            android.util.Log.e("PermissionManager", "Error launching application details settings", e)
        }
    }
}
