package mx.integritas.challenge

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.GridLayoutManager
import mx.integritas.challenge.adapter.ImagePickerAdapter
import mx.integritas.challenge.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initView()
        showPermissions {
            if (!it) {
                askForCameraPermission()
            }
        }
    }

    private val REQUEST_SELECT_IMAGE_IN_ALBUM = 1
    private lateinit var adapter: ImagePickerAdapter
    private var chosenImageUris = mutableListOf<Uri>()

    private fun initView() {
        with(binding) {

            adapter = ImagePickerAdapter(this@MainActivity, chosenImageUris)
            rvImagePicker.adapter = adapter
            rvImagePicker.setHasFixedSize(true)
            rvImagePicker.layoutManager = GridLayoutManager(this@MainActivity, 3)

            btnLoadImage.setOnClickListener {
                openGalleryForPickingImage(REQUEST_SELECT_IMAGE_IN_ALBUM)
            }
            btnTakePicture.setOnClickListener {
                showPermissions {
                    if (!it) {
                        askForCameraPermission()
                    } else {
                        startActivity(
                            Intent(
                                this@MainActivity,
                                ScannerActivity::class.java
                            )
                        )
                    }
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @Deprecated("")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != REQUEST_SELECT_IMAGE_IN_ALBUM || resultCode != Activity.RESULT_OK || data == null) {
            return
        }
        val selectedUri = data.data
        val clipData = data.clipData
        if (clipData != null) {
            for (i in 0 until clipData.itemCount) {
                val clipItem = clipData.getItemAt(i)
                chosenImageUris.add(clipItem.uri)
            }
        } else if (selectedUri != null) {
            chosenImageUris.add(selectedUri)
        }
        adapter.notifyDataSetChanged()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestCodeCameraPermission && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //setupControls()
            } else {
                Toast.makeText(applicationContext, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
