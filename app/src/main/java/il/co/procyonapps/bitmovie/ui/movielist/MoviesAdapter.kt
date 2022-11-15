package il.co.procyonapps.bitmovie.ui.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import il.co.procyonapps.bitmovie.databinding.MovieListItemBinding
import il.co.procyonapps.bitmovie.model.BasicMovie

class MoviesAdapter(val lifecycle: LifecycleOwner, val onItemClick: (movie: BasicMovie) -> Unit, val onFavoriteSelected: (movie: BasicMovie) -> Unit) : PagingDataAdapter<BasicMovie,
        MoviesAdapter
        .MovieViewHolder>
    (MovieDiff) {
    val TAG = this::class.simpleName ?: "Unspecified"
    
    
    object MovieDiff : DiffUtil.ItemCallback<BasicMovie>() {
        override fun areItemsTheSame(oldItem: BasicMovie, newItem: BasicMovie): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: BasicMovie, newItem: BasicMovie): Boolean = oldItem == newItem
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binder = MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binder)
    }
    
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.binder.movie = movie
        holder.binder.lifecycleOwner = lifecycle
        
        movie?.also { mov ->
            holder.binder.root.setOnClickListener { onItemClick(mov) }
            holder.binder.btFavorite.setOnClickListener {
                onFavoriteSelected(mov)
            }
        }
    }
    
    inner class MovieViewHolder(val binder: MovieListItemBinding) : ViewHolder(binder.root)
}