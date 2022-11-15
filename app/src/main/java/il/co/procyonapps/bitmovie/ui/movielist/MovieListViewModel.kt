package il.co.procyonapps.bitmovie.ui.movielist

import androidx.lifecycle.*
import androidx.paging.*
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import il.co.procyonapps.bitmovie.api.TmdbApi
import il.co.procyonapps.bitmovie.api.responses.MovieListResponse
import il.co.procyonapps.bitmovie.db.FavoriteMovieEntity
import il.co.procyonapps.bitmovie.db.FavoriteMoviesDao
import il.co.procyonapps.bitmovie.model.BasicMovie
import il.co.procyonapps.bitmovie.model.FilterType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val api: TmdbApi, private val dbDao: FavoriteMoviesDao) : ViewModel() {
    val TAG = this::class.simpleName ?: "Unspecified"
    
    //this is the source of truth for the selected filter. it is mutable because it is mutated by the binding function hiding in XML layout
    val selectedFilter = MutableLiveData<FilterType>(FilterType.UP_COMING)
    
    private val filterFlow = selectedFilter
        .asFlow()
        .stateIn(viewModelScope, SharingStarted.Eagerly, FilterType.UP_COMING)
    
    private val favoritesFlow = dbDao.getAllFavoriteMovies().distinctUntilChanged()
    
    val favorites = favoritesFlow
        .map {
            it.map { schema -> BasicMovie.fromEntity(schema) }
        }
        .asLiveData()
    
    val finalMovies = filterFlow
        .distinctUntilChanged { old, new -> old == new }
        .flatMapLatest {
            when (it) {
                FilterType.UP_COMING -> upcomingMoviesPager
                FilterType.TOP_RATED -> topRatedMoviesPager
                FilterType.NOW_PLAYING -> nowPlayingMoviesPager
            }
        }
        .combine(favoritesFlow) { results: PagingData<BasicMovie>, favorites: List<FavoriteMovieEntity> ->
            results.map {
                it.isFavorite.value = favorites.any { fav -> fav.id == it.id }
                it
            }
        }
        .asLiveData()
    
    fun switchMovieIsFavorite(mov: BasicMovie) {
        viewModelScope.launch {
            if (mov.isFavorite.value == true) {
                dbDao.deleteMovie(mov.id)
            } else {
                dbDao.insertMovie(mov.toEntity())
            }
        }
    }
    
    private val upcomingMoviesPager: Flow<PagingData<BasicMovie>> by lazy {
        movieFlow(api::getUpcomingMovies)
    }
    private val topRatedMoviesPager: Flow<PagingData<BasicMovie>> by lazy {
        movieFlow(api::getTopRated)
    }
    private val nowPlayingMoviesPager: Flow<PagingData<BasicMovie>> by lazy {
        movieFlow(api::getNowPlaying)
    }
    
    private fun movieFlow(apiCall: suspend (page: Int) -> NetworkResponse<MovieListResponse, Any>): Flow<PagingData<BasicMovie>> = Pager(PagingConfig(0/*isn't used by pager*/, prefetchDistance = 1), 1) {
        MoviesPager(apiCall)
    }
        .flow
        .flowOn(Dispatchers.IO)
        .cachedIn(viewModelScope)
}