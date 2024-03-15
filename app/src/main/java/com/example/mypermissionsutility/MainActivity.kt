package com.example.mypermissionsutility

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mypermissionsutility.databinding.ActivityMainBinding
import com.example.mypermissionsutility.facade.OnPermissionListener
import com.example.mypermissionsutility.facade.PermissionsIdentifier
import com.example.mypermissionsutility.facade.PermissionsUtility

class MainActivity : AppCompatActivity(), OnPermissionListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var permissionsUtility: PermissionsUtility

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPermissionsUtility()

        binding.apply {
            btnNotifications.setOnClickListener {
                if (permissionsUtility.checkPermission.postNotifications()) {
                    Toast.makeText(this@MainActivity, "Notifications already granted", Toast.LENGTH_SHORT).show()
                } else {
                    permissionsUtility.requestPostNotifications()
                }
            }

            btnLocation.setOnClickListener {
                if (permissionsUtility.checkPermission.fineLocation()) {
                    Toast.makeText(this@MainActivity, "Location already granted", Toast.LENGTH_SHORT).show()
                } else {
                    permissionsUtility.requestFineLocation()
                }
            }

            btnCamera.setOnClickListener {
                if (permissionsUtility.checkPermission.camera()) {
                    Toast.makeText(this@MainActivity, "Camera already granted", Toast.LENGTH_SHORT).show()
                } else {
                    permissionsUtility.requestCamera()
                }
            }
        }
    }

    private fun initPermissionsUtility() {
        permissionsUtility = PermissionsUtility(this@MainActivity)
        permissionsUtility.setPermissionsCallBack(this@MainActivity)
    }

    override fun onPermissionListener(permission: PermissionsIdentifier, granted: Boolean) {
        when (permission) {
            PermissionsIdentifier.POST_NOTIFICATIONS -> {
                if (granted) {
                    Toast.makeText(this@MainActivity, "Notifications granted", Toast.LENGTH_SHORT).show()
                } else {
                    permissionsUtility.settingsOpener.notificationSettings()
                }
            }

            PermissionsIdentifier.CAMERA -> {
                if (granted) {
                    Toast.makeText(this@MainActivity, "Camera granted", Toast.LENGTH_SHORT).show()
                } else {
                    permissionsUtility.settingsOpener.cameraSettings()
                }
            }

            PermissionsIdentifier.FINE_LOCATION -> {
                if (granted) {
                    Toast.makeText(this@MainActivity, "Location granted", Toast.LENGTH_SHORT).show()
                }
            }

            PermissionsIdentifier.COARSE_LOCATION -> {
                if (granted) {
                    permissionsUtility.settingsOpener.preciseLocationSettings()
                } else {
                    permissionsUtility.settingsOpener.locationSettings()
                }
            }
        }

    }
}