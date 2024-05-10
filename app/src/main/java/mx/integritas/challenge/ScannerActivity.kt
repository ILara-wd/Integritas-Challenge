package mx.integritas.challenge

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import com.google.android.gms.vision.CameraSource
import com.google.android.gms.vision.Detector
import com.google.android.gms.vision.Detector.Detections
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import mx.integritas.challenge.databinding.ActivityScannerBinding
import java.io.IOException

class ScannerActivity : BaseActivity() {

    private lateinit var cameraSource: CameraSource
    private lateinit var barcodeDetector: BarcodeDetector
    private var scannedValue = ""
    private lateinit var binding: ActivityScannerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityScannerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        showPermissions {
            if (it) {
                setupControls()
            } else {
                askForCameraPermission()
            }
        }
        showAnimations()
    }


    private fun showAnimations() {
        val aniSlide: Animation =
            AnimationUtils.loadAnimation(this@ScannerActivity, R.anim.scanner_animation)
        binding.barcodeLine.startAnimation(aniSlide)
    }

    private fun setupControls() {
        barcodeDetector =
            BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.ALL_FORMATS).build()

        cameraSource = CameraSource.Builder(this, barcodeDetector)
            .setRequestedPreviewSize(1920, 1080)
            .setAutoFocusEnabled(true)
            .build()

        binding.cameraSurfaceView.holder.addCallback(object : SurfaceHolder.Callback {
            @SuppressLint("MissingPermission")
            override fun surfaceCreated(holder: SurfaceHolder) {
                try {
                    cameraSource.start(holder)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            @SuppressLint("MissingPermission")
            override fun surfaceChanged(
                holder: SurfaceHolder,
                format: Int,
                width: Int,
                height: Int
            ) {
                try {
                    cameraSource.start(holder)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                cameraSource.stop()
            }
        })


        barcodeDetector.setProcessor(object : Detector.Processor<Barcode> {
            override fun release() {
                Toast.makeText(applicationContext, "Scanner has been closed", Toast.LENGTH_SHORT)
                    .show()
            }

            override fun receiveDetections(detections: Detections<Barcode>) {
                val barcodes = detections.detectedItems
                if (barcodes.size() == 1) {
                    scannedValue = barcodes.valueAt(0).rawValue
                    runOnUiThread {
                        cameraSource.stop()
                        Toast.makeText(
                            this@ScannerActivity,
                            "value- $scannedValue",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                } else {
                    Toast.makeText(this@ScannerActivity, "value- else", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestCodeCameraPermission && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setupControls()
            } else {
                Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraSource.stop()
    }

}