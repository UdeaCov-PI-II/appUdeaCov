package co.edu.udea.udeacov.fragmentos.lista_solicitudes

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri()
        Glide.with(imgView.context)
            .load(imgUri)
            .into(imgView)
    }
}