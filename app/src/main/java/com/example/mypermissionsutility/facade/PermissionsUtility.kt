package com.example.mypermissionsutility.facade

import android.Manifest
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class PermissionsUtility(activity: AppCompatActivity) {

    val checkPermission = CheckPermission(activity)
    val settingsOpener = SettingsOpener(activity)

    private var listener: OnPermissionListener? = null

    private val requestPermission =
        activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (it[Manifest.permission.POST_NOTIFICATIONS] == true) {
                listener?.onPermissionListener(PermissionsIdentifier.POST_NOTIFICATIONS, true)
            } else if (it[Manifest.permission.POST_NOTIFICATIONS] == false) {
                listener?.onPermissionListener(PermissionsIdentifier.POST_NOTIFICATIONS, false)
            }

            // else

            // Precise granted
            if (it[Manifest.permission.ACCESS_FINE_LOCATION] == true && it[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
                listener?.onPermissionListener(PermissionsIdentifier.FINE_LOCATION, true)

                // Coarse granted
            } else if (it[Manifest.permission.ACCESS_FINE_LOCATION] == false || it[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
                listener?.onPermissionListener(PermissionsIdentifier.COARSE_LOCATION, true)


                // Location denied
            } else if (it[Manifest.permission.ACCESS_FINE_LOCATION] == false || it[Manifest.permission.ACCESS_COARSE_LOCATION] == false) {
                listener?.onPermissionListener(PermissionsIdentifier.COARSE_LOCATION, false)
            }

            //else

            if (it[Manifest.permission.CAMERA] == true) {
                listener?.onPermissionListener(PermissionsIdentifier.CAMERA, true)
            } else if (it[Manifest.permission.CAMERA] == false) {
                listener?.onPermissionListener(PermissionsIdentifier.CAMERA, false)
            }
        }


    fun requestPostNotifications() {
        if (Build.VERSION.SDK_INT >= 33) {
            requestPermission.launch(arrayOf(Manifest.permission.POST_NOTIFICATIONS))
        }
    }

    fun requestFineLocation() {
        if (!checkPermission.fineLocation()) {
            requestPermission.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        } else {
            listener?.onPermissionListener(PermissionsIdentifier.FINE_LOCATION, true)
        }
    }

    fun requestCamera() {
        requestPermission.launch(
            arrayOf(Manifest.permission.CAMERA)
        )
    }

    fun setPermissionsCallBack(implementer: OnPermissionListener) {
        this@PermissionsUtility.listener = implementer
    }
}