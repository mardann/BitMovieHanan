package il.co.procyonapps.bitmovie.ui.moviedetails

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerFragment
import dagger.hilt.android.AndroidEntryPoint
import il.co.procyonapps.bitmovie.R
import il.co.procyonapps.bitmovie.api.APIConst
import il.co.procyonapps.bitmovie.databinding.FragmentMovieDetailsBinding
import il.co.procyonapps.bitmovie.ui.viewBinding
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.fragment_movie_details) {
    val TAG = this::class.simpleName ?: "Unspecified"
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
            viewModel = this@MovieDetailFragment.viewModel
            tvDescription.movementMethod = ScrollingMovementMethod()
        }
        
        binder!!.ytVideoPlayer.setOnClickListener {
            viewModel.video.value?.also {videoKey ->
                val dir = MovieDetailFragmentDirections.actionMovieDetailFragToYouTubeActivity(videoKey)
                findNavController().navigate(dir)
            }
        }
    }
}