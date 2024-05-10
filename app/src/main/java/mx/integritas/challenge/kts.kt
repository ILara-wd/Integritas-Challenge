package mx.integritas.challenge

import android.app.Activity
import android.content.Intent

fun Activity.openGalleryForPickingImage(code: Int) {
    Intent().apply {
        action = Intent.ACTION_PICK
        type = "image/*"
        putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(Intent.createChooser(this, getString(R.string.select_file)), code)
    }
}
