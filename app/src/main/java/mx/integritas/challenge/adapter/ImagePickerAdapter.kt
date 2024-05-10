package mx.integritas.challenge.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import mx.integritas.challenge.R

class ImagePickerAdapter(
    private val context: Context,
    private val imageUris: List<Uri>
) : RecyclerView.Adapter<ImagePickerAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.i(TAG, "onCreateViewHolder")
        val view = LayoutInflater.from(context).inflate(R.layout.card_image, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = imageUris.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageUris[position])

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivCustomImage = itemView.findViewById<ImageView>(R.id.ivCustomImage)

        fun bind(uri: Uri) {
            ivCustomImage.setImageURI(uri)
            ivCustomImage.setOnClickListener(null)
        }

    }
}