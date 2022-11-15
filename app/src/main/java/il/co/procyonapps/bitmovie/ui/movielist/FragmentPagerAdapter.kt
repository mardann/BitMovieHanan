package il.co.procyonapps.bitmovie.ui.movielist

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentPagerAdapter(fragment: Fragment):FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> MovieListFragment.newInstance()
            1 -> FavoritesFragment.newInstance()
            else -> throw IllegalStateException("No definition for position $position")
        }
    }
}