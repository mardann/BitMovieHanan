package il.co.procyonapps.bitmovie.ui.movielist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagingDataAdapter
import il.co.procyonapps.bitmovie.databinding.MovieListItemBinding
import il.co.procyonapps.bitmovie.model.BasicMovie
import il.co.procyonapps.bitmovie.ui.movielist.MovieDiff

class MoviesPagingListAdapter(val lifecycle: LifecycleOwner, val onItemClick: (movie: BasicMovie) -> Unit, val onFavoriteSelected: (movie: BasicMovie) -> Unit) : PagingDataAdapter<BasicMovie,
        MovieViewHolder>
    (MovieDiff) {
    val TAG = this::class.simpleName ?: "Unspecified"
    
    
    
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
    
    
}