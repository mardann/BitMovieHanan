package il.co.procyonapps.bitmovie.ui.movielist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import il.co.procyonapps.bitmovie.R
import il.co.procyonapps.bitmovie.databinding.FragmentMovieListBinding
import il.co.procyonapps.bitmovie.model.BasicMovie
import il.co.procyonapps.bitmovie.ui.viewBinding

@AndroidEntryPoint
class MovieListFragment : Fragment(R.layout.fragment_movie_list) {
    val viewModel: MovieListViewModel by viewModels()
    private val binder by viewBinding(FragmentMovieListBinding::bind)
    private val listAdapter by lazy { MoviesAdapter(viewLifecycleOwner, onItemClick, onItemFavoriteClicked) }
    private val onItemClick: (BasicMovie) -> Unit = {
//        findNavController().navigate()
    }
    private val onItemFavoriteClicked: (BasicMovie) -> Unit = {
        viewModel.switchMovieIsFavorite(it)
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binder!!.viewModel = viewModel
        binder!!.lifecycleOwner = viewLifecycleOwner
        binder!!.rvMovies.apply {
            adapter = listAdapter
            (layoutManager as GridLayoutManager).apply {
                orientation = RecyclerView.VERTICAL
                this.spanCount = 3
            }
        }
        
        viewModel.finalMovies.observe(viewLifecycleOwner) {
            listAdapter.submitData(lifecycle, it)
        }
    }
}