package com.example.flashlightwidget

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val CAMERA_PERMISSION = 200
    var flashLightStatus: Boolean = false
    var btAction: ImageButton? = null
    var tvStatus: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




    }

    override fun onResume() {
        super.onResume()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val permissions = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)

            if (permissions != PackageManager.PERMISSION_GRANTED)
                setupPermissions()
            else {
                openFlashLight()
            }
        } else {
            openFlashLight()
        }
        tvStatus!!.setText("ON")

    }


    private fun setupPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            CAMERA_PERMISSION -> {
                if (grantResults.isEmpty() || !grantResults[0].equals(PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show()
                } else {
                    openFlashLight()
                }
            }
        }
    }


    private fun openFlashLight() {
        val cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val cameraId = cameraManager.cameraIdList[0]

            try {
                cameraManager.setTorchMode(cameraId, true)
                btAction!!.setImageDrawable(getDrawable(R.drawable.ic_launcher_background))
                tvStatus!!.setText("ON")
                flashLightStatus = true

            } catch (e: CameraAccessException) {
            }

    }
}