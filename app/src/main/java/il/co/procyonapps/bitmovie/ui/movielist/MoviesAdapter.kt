package il.co.procyonapps.bitmovie.ui.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import il.co.procyonapps.bitmovie.databinding.MovieListItemBinding
import il.co.procyonapps.bitmovie.model.BasicMovie

class MoviesAdapter: PagingDataAdapter<BasicMovie, MoviesAdapter.MovieViewHolder>(MovieDiff) {
    
    object MovieDiff: DiffUtil.ItemCallback<BasicMovie>(){
        override fun areItemsTheSame(oldItem: BasicMovie, newItem: BasicMovie): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: BasicMovie, newItem: BasicMovie): Boolean = oldItem == newItem
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binder = MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binder)
    }
    
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
       holder.binder.movie = getItem(position)
    }
    inner class MovieViewHolder(val binder: MovieListItemBinding): ViewHolder(binder.root)
}