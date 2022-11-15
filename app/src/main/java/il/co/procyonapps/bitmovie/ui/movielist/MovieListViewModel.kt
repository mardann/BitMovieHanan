package il.co.procyonapps.bitmovie.ui.movielist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import il.co.procyonapps.bitmovie.api.TmdbApi
import il.co.procyonapps.bitmovie.model.BasicMovie
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(val api: TmdbApi) : ViewModel() {
    val TAG = this::class.simpleName ?: "Unspecified"
    val recentMovies = liveData {
        val result = api.getTopRated(1)
        
        when (result) {
            is NetworkResponse.Success -> {
                val mappded = result
                    .body
                    .results
                    .map { BasicMovie.fromApiResponse(it) }
                emit(mappded)
            }
            is NetworkResponse.NetworkError -> {
                Log.w(TAG, "Network error getting Top Rated movies:", result.error)
            }
            is NetworkResponse.ServerError -> {
                Log.w(TAG, "Server error getting Top Rated movies: code = ${result.code}")
            }
            else -> {
                Log.w(TAG, "Unknown error: ${result}")
            }
        }
    }
}