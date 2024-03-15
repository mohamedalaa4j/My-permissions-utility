package com.example.mypermissionsutility.facade

interface OnPermissionListener {
    fun onPermissionListener(permission: PermissionsIdentifier, granted: Boolean)
}