package il.co.procyonapps.bitmovie.ui.movielist

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import il.co.procyonapps.bitmovie.api.TmdbApi
import il.co.procyonapps.bitmovie.model.BasicMovie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(val api: TmdbApi) : ViewModel() {
    val TAG = this::class.simpleName ?: "Unspecified"
    
    val recentMoviesPager: LiveData<PagingData<BasicMovie>> by lazy {
        Pager(PagingConfig(20, prefetchDistance = 1), 1) {
            MoviesPagerAdapter(api::getTopRated)
        }
            .flow
            .flowOn(Dispatchers.IO)
            .cachedIn(viewModelScope)
            .asLiveData()
    }
}