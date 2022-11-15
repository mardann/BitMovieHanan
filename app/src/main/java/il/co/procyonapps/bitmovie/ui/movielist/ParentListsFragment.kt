package il.co.procyonapps.bitmovie.ui.movielist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import il.co.procyonapps.bitmovie.R
import il.co.procyonapps.bitmovie.databinding.FragmentParentMovieListBinding
import il.co.procyonapps.bitmovie.ui.viewBinding

@AndroidEntryPoint
class ParentListsFragment: Fragment(R.layout.fragment_parent_movie_list) {
    
    val binder by viewBinding(FragmentParentMovieListBinding::bind)
    val viewModel: MovieListViewModel by viewModels()
    val fragPager by lazy { FragmentPagerAdapter(this) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binder!!.pager.adapter = fragPager
        
        TabLayoutMediator(binder!!.tabLayout, binder!!.pager){ tab, position ->
            tab.text = when (position){
                0 -> "Movies"
                1 -> "Favorites"
                else -> throw IllegalStateException("No definition for position $position")
            }
        }.attach()
        
    }
}