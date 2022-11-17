package il.co.procyonapps.bitmovie.ui.moviedetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import il.co.procyonapps.bitmovie.api.TmdbApi
import il.co.procyonapps.bitmovie.api.responses.VideosResult
import il.co.procyonapps.bitmovie.model.MovieDetail

class MovieDetailsViewModel @AssistedInject constructor(@Assisted private val movieId: Int, private val api: TmdbApi) : ViewModel() {
    val TAG = this::class.simpleName ?: "Unspecified"
    val movieDetail: LiveData<MovieDetail> = liveData {
        when (val response = api.getMovieDetails("$movieId")) {
            is NetworkResponse.Success -> {
                emit(MovieDetail.fromApi(response.body))
            }
            is NetworkResponse.NetworkError -> {
                Log.e(TAG, "load: network error", response.error)
            }
            is NetworkResponse.ServerError -> {
                val throwable = Exception("Failed to call API. Error code=${response.code}")
                Log.e(TAG, "load: Server Error", throwable)
            }
            is NetworkResponse.UnknownError -> {
                val throwable = Exception("Unknown Error. Error code=${response.code}", response.error)
                Log.e(TAG, "load: Unknown error", throwable)
            }
        }
    }
    val video: LiveData<String?> = liveData {
        emit(null)
        val response = api.getMovieVideos(movieId.toString())
        
        when (response) {
            is NetworkResponse.Success -> {
                val videos: List<VideosResult.Result> = response
                    .body
                    .results
                    .filter {
                        it.site.equals("YouTube", true)
                    }
                val selectedVideo =
                    videos.find { it.type == "Official Trailer" }
                        ?: videos.find { it.type == "Trailer" }
                        ?:videos.find { it.type == "Teaser" }
                        ?:videos.firstOrNull()
                emit(selectedVideo?.key)
            }
            is NetworkResponse.NetworkError -> {
                Log.e(TAG, "load: network error", response.error)
                emit(null)
            }
            is NetworkResponse.ServerError -> {
                val throwable = Exception("Failed to call API. Error code=${response.code}")
                Log.e(TAG, "load: Server Error", throwable)
                emit(null)
            }
            is NetworkResponse.UnknownError -> {
                val throwable = Exception("Unknown Error. Error code=${response.code}", response.error)
                Log.e(TAG, "load: Unknown error", throwable)
                emit(null)
            }
            
        }
    }
    
    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(id: Int): MovieDetailsViewModel
    }
    
    companion object {
        fun provideFactory(factory: AssistedFactory, movieId: Int): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T = factory.create(movieId) as T
            }
        }
    }
}