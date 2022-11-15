package il.co.procyonapps.bitmovie.ui.movielist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import il.co.procyonapps.bitmovie.R
import il.co.procyonapps.bitmovie.databinding.FragmentFavoritesBinding
import il.co.procyonapps.bitmovie.databinding.FragmentMovieListBinding
import il.co.procyonapps.bitmovie.model.BasicMovie
import il.co.procyonapps.bitmovie.ui.movielist.adapters.MoviesListAdapter
import il.co.procyonapps.bitmovie.ui.movielist.adapters.MoviesPagingListAdapter
import il.co.procyonapps.bitmovie.ui.viewBinding

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {
    companion object{
        fun newInstance(): FavoritesFragment{
            
            val fragment = FavoritesFragment()
            
            return fragment
        }
    }
    
    val viewModel: MovieListViewModel by viewModels({requireParentFragment()})
    private val binder by viewBinding(FragmentFavoritesBinding::bind)
    private val listAdapter by lazy { MoviesListAdapter(viewLifecycleOwner, onItemClick, onItemFavoriteClicked) }
    private val onItemClick: (BasicMovie) -> Unit = {
//        findNavController().navigate()
    }
    private val onItemFavoriteClicked: (BasicMovie) -> Unit = {
        viewModel.switchMovieIsFavorite(it)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        
        binder!!.rvFavorites.apply {
            adapter = listAdapter
            (layoutManager as GridLayoutManager).apply {
                orientation = RecyclerView.VERTICAL
                this.spanCount = 3
            }
        }
        
        viewModel.favorites.observe(viewLifecycleOwner) {
            listAdapter.submitList(it)
        }
    }
}