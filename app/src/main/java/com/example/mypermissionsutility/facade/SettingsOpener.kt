package com.example.mypermissionsutility.facade

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings

class SettingsOpener(private val context: Context) {

    fun notificationSettings() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Enable notifications")
        builder.setMessage("Notification permission is denied\nYou can enable it from the settings.")
        builder.setPositiveButton("Go to settings") { dialog, _ ->
            notificationSettingsIntent()
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun notificationSettingsIntent() {
        val intent = Intent()

        // For Android 5.0 (Lollipop) and above
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
        }
        // For older Android versions
        else {
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.data = Uri.parse("package:" + context.packageName)
        }

        context.startActivity(intent)
    }

    fun locationSettings() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Enable location")
        builder.setMessage("Location access is denied\nYou can enable it from the settings.")
        builder.setPositiveButton("Go to settings") { dialog, _ ->
            settingsIntent()
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    fun preciseLocationSettings() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Enable location")
        builder.setMessage("Precise location is highly preferred for app functionality\nYou can enable it from the settings.")
        builder.setPositiveButton("Go to settings") { dialog, _ ->
            settingsIntent()
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    fun cameraSettings() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Camera permission")
        builder.setMessage("Camera access is denied\nYou can enable it from the settings.")
        builder.setPositiveButton("Go to settings") { dialog, _ ->
            settingsIntent()
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun settingsIntent() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri: Uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }
}