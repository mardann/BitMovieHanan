package il.co.procyonapps.bitmovie.ui.moviedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import il.co.procyonapps.bitmovie.R
import il.co.procyonapps.bitmovie.databinding.FragmentSecondBinding
import il.co.procyonapps.bitmovie.ui.viewBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */

@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.fragment_second) {
    
    val args: MovieDetailFragmentArgs by navArgs()
    private val binding by viewBinding(FragmentSecondBinding::bind)
    
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding!!.textviewSecond.text = "${args.movieId}"
        
        
    }
    
}