package mx.integritas.challenge

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

open class BaseActivity : AppCompatActivity() {

    val requestCodeCameraPermission = 1001

    fun showPermissions(callback: (result: Boolean) -> Unit) {
        if (ContextCompat.checkSelfPermission(
                this@BaseActivity, android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            callback(false)
        } else {
            callback(true)
        }
    }

    fun askForCameraPermission() {
        ActivityCompat.requestPermissions(
            this@BaseActivity,
            arrayOf(android.Manifest.permission.CAMERA),
            requestCodeCameraPermission
        )
    }

}