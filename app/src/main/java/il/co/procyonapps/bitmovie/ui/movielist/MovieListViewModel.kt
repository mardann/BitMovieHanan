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
import il.co.procyonapps.bitmovie.api.responses.MovieListResponse
import il.co.procyonapps.bitmovie.model.BasicMovie
import il.co.procyonapps.bitmovie.model.FilterType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(val api: TmdbApi) : ViewModel() {
    val TAG = this::class.simpleName ?: "Unspecified"
    
    //this is the source of truth for the selected filter. it is mutable because it is mutated by the binding function hiding in XML layout
    val selectedFilter = MutableLiveData<FilterType>(FilterType.UP_COMING)
    
    private val filterFlow = selectedFilter.asFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, FilterType.UP_COMING)
    
//    init {
//        selectedFilter.observeForever {
//            Log.d(TAG, "Selected filter: $it")
//        }
//
//        viewModelScope.launch {
//            filterFlow.collectLatest {
//                Log.d(TAG, "filterFlow: $it")
//            }
//        }
//    }
    
    val finalMovies = filterFlow
        .distinctUntilChanged { old, new -> old == new }
        .flatMapLatest {
            when (it){
                FilterType.UP_COMING -> upcomingMoviesPager
                FilterType.TOP_RATED -> topRatedMoviesPager
                FilterType.NOW_PLAYING -> nowPlayingMoviesPager
            }
        }
        .asLiveData()
    
    private val upcomingMoviesPager: Flow<PagingData<BasicMovie>> by lazy {
        movieFlow(api::getUpcomingMovies)
            
    }
   private  val topRatedMoviesPager: Flow<PagingData<BasicMovie>> by lazy {
       movieFlow(api::getTopRated)
    }
    private val nowPlayingMoviesPager: Flow<PagingData<BasicMovie>> by lazy {
        movieFlow(api::getNowPlaying)
    }
    
    private fun movieFlow( apiCall: suspend (page :Int) -> NetworkResponse<MovieListResponse, Any>) = Pager(PagingConfig(0/*isn't used by pager*/, prefetchDistance = 1), 1) {
        MoviesPagerAdapter(apiCall)
    }
        .flow
        .flowOn(Dispatchers.IO)
        .cachedIn(viewModelScope)
}