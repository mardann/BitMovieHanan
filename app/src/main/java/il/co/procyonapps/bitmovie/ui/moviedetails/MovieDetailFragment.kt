package il.co.procyonapps.bitmovie.ui.moviedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import il.co.procyonapps.bitmovie.R
import il.co.procyonapps.bitmovie.databinding.FragmentMovieDetailsBinding
import il.co.procyonapps.bitmovie.ui.viewBinding
import javax.inject.Inject


@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.fragment_movie_details) {
    private val args: MovieDetailFragmentArgs by navArgs()
    private val movieId by lazy { args.movieId }
    
    @Inject
    lateinit var factory: MovieDetailsViewModel.AssistedFactory
    val viewModel: MovieDetailsViewModel by viewModels { MovieDetailsViewModel.provideFactory(factory, movieId) }
    private val binder by viewBinding(FragmentMovieDetailsBinding::bind)
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binder!!.apply {
            lifecycleOwner = viewLifecycleOwner
        }
        
        viewModel.movieDetail.observe(viewLifecycleOwner){
            binder?.movie = it
        }
    }
}