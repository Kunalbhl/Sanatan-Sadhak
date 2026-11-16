package com.example.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import android.util.Log

data class OemBatteryGuidance(
    val oemName: String,
    val headline: String,
    val explanation: String,
    val stepGuide: List<String>,
    val requiresManualWhitelist: Boolean
)

/**
 * Centralized BatteryOptimizationManager for Sanatan Sadhak.
 * Helps ensure accurate delivery of Brahma Muhurta, Sandhya, and Daily Shloka reminders
 * across aggressive OEM battery managers (Samsung OneUI, Xiaomi HyperOS/MIUI, OnePlus OxygenOS, etc.)
 *
 * Adheres strictly to:
 * - NO automatic system settings popups.
 * - Only graceful devotional guidance when explicitly needed.
 * - Persistent dismissal preferences per feature.
 * - Safe Intent handling for diverse OEM skins.
 */
object BatteryOptimizationManager {

    private const val TAG = "BatteryOptManager"
    private const val PREFS_NAME = "sadhak_battery_prefs"
    private const val KEY_PREFIX_DISMISSED = "dismissed_guidance_"

    fun isIgnoringBatteryOptimizations(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val powerManager = context.getSystemService(Context.POWER_SERVICE) as? PowerManager
            powerManager?.isIgnoringBatteryOptimizations(context.packageName) ?: true
        } else {
            true
        }
    }

    fun hasDismissedGuidance(context: Context, featureKey: String): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean("$KEY_PREFIX_DISMISSED$featureKey", false)
    }

    fun dismissGuidance(context: Context, featureKey: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean("$KEY_PREFIX_DISMISSED$featureKey", true).apply()
    }

    fun resetDismissedGuidance(context: Context, featureKey: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().remove("$KEY_PREFIX_DISMISSED$featureKey").apply()
    }

    fun getOemGuidance(context: Context, featureName: String = "Devotional Alarms"): OemBatteryGuidance {
        val manufacturer = Build.MANUFACTURER?.lowercase() ?: "android"
        val isOptimized = !isIgnoringBatteryOptimizations(context)

        return when {
            manufacturer.contains("samsung") -> OemBatteryGuidance(
                oemName = "Samsung (One UI)",
                headline = "Ensure Timely $featureName on Samsung",
                explanation = "One UI may put background apps to sleep. To receive timely Brahma Muhurta and Sandhya Aarti reminders without delay, allow Unrestricted battery usage.",
                stepGuide = listOf(
                    "Open App Info -> Battery",
                    "Select 'Unrestricted'",
                    "Under Device Care -> Battery -> Background usage limits, ensure Sanatan Sadhak is not in 'Deep sleeping apps'"
                ),
                requiresManualWhitelist = isOptimized
            )
            manufacturer.contains("xiaomi") || manufacturer.contains("redmi") || manufacturer.contains("poco") -> OemBatteryGuidance(
                oemName = "Xiaomi / Redmi / Poco (HyperOS / MIUI)",
                headline = "Ensure Timely $featureName on HyperOS/MIUI",
                explanation = "HyperOS/MIUI restricts background tasks. Enable Autostart and set Battery Saver to 'No restrictions' for punctual devotional reminders.",
                stepGuide = listOf(
                    "App Info -> Enable 'Autostart'",
                    "App Info -> Battery Saver -> Select 'No restrictions'",
                    "Lock Sanatan Sadhak in Recent Apps tray if needed"
                ),
                requiresManualWhitelist = isOptimized
            )
            manufacturer.contains("oneplus") -> OemBatteryGuidance(
                oemName = "OnePlus (OxygenOS)",
                headline = "Ensure Timely $featureName on OxygenOS",
                explanation = "OxygenOS optimizes background apps aggressively. Allow background activity so sacred reminders alert you at exact muhurtas.",
                stepGuide = listOf(
                    "App Info -> Battery -> Battery usage",
                    "Allow 'Background activity' & 'Auto-launch'",
                    "Optimize battery use -> Sanatan Sadhak -> 'Don't optimize'"
                ),
                requiresManualWhitelist = isOptimized
            )
            manufacturer.contains("oppo") || manufacturer.contains("realme") -> OemBatteryGuidance(
                oemName = "Oppo / Realme (ColorOS / Realme UI)",
                headline = "Ensure Timely $featureName on ColorOS / Realme UI",
                explanation = "ColorOS and Realme UI freeze background services. Permit background execution to receive sacred daily reminders reliably.",
                stepGuide = listOf(
                    "App Info -> Battery -> Allow background activity",
                    "App Info -> Enable 'Auto startup'",
                    "Battery -> More settings -> App battery management -> Disable freeze"
                ),
                requiresManualWhitelist = isOptimized
            )
            manufacturer.contains("vivo") || manufacturer.contains("iqoo") -> OemBatteryGuidance(
                oemName = "Vivo / iQOO (Funtouch OS)",
                headline = "Ensure Timely $featureName on Funtouch OS",
                explanation = "Funtouch OS restricts high background power apps. Grant high background consumption permission for exact devotional timings.",
                stepGuide = listOf(
                    "Settings -> Battery -> Background power consumption management",
                    "Find Sanatan Sadhak -> Select 'High background power consumption'",
                    "Enable Autostart in Permission Management"
                ),
                requiresManualWhitelist = isOptimized
            )
            else -> OemBatteryGuidance(
                oemName = "Android (${Build.MANUFACTURER ?: "Stock"})",
                headline = "Ensure Timely $featureName",
                explanation = "Battery Saver and Doze mode can delay time-sensitive notifications. Allowing unrestricted battery ensures sacred alarms ring precisely on time.",
                stepGuide = listOf(
                    "App Info -> App battery usage",
                    "Select 'Unrestricted'"
                ),
                requiresManualWhitelist = isOptimized
            )
        }
    }

    /**
     * Safely opens battery optimization or application details settings on user request.
     * Never called automatically or silently.
     */
    @SuppressLint("BatteryLife")
    fun requestIgnoreBatteryOptimization(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
                    data = Uri.parse("package:${context.packageName}")
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
                return
            } catch (e: Exception) {
                Log.w(TAG, "ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS failed, falling back", e)
            }

            try {
                val intent = Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                context.startActivity(intent)
                return
            } catch (e: Exception) {
                Log.w(TAG, "ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS failed, falling back", e)
            }
        }

        // Standard App Settings fallback
        PermissionManager.openAppSettings(context)
    }
}
