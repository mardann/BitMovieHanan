package il.co.procyonapps.bitmovie.ui.movielist

import androidx.recyclerview.widget.DiffUtil
import il.co.procyonapps.bitmovie.model.BasicMovie

object MovieDiff : DiffUtil.ItemCallback<BasicMovie>() {
    override fun areItemsTheSame(oldItem: BasicMovie, newItem: BasicMovie): Boolean = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: BasicMovie, newItem: BasicMovie): Boolean = oldItem == newItem
}