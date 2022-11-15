package il.co.procyonapps.bitmovie.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("setImageUrl")
fun ImageView.setImage(url: String?) {
    if (url == null) return
    Glide.with(this)
        .load(url)
        .centerCrop()
        .into(this)
}