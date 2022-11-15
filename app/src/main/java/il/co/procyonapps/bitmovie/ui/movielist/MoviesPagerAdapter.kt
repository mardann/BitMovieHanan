package il.co.procyonapps.bitmovie.ui.movielist

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.haroldadmin.cnradapter.NetworkResponse
import il.co.procyonapps.bitmovie.api.responses.MovieListResponse
import il.co.procyonapps.bitmovie.model.BasicMovie

class MoviesPagerAdapter(val movieLoader: suspend (index: Int) -> NetworkResponse<MovieListResponse, Any>): PagingSource<Int, BasicMovie>() {
    private val TAG = this::class.simpleName ?: "Unspecified"
    private var pageIndex = 1
    override fun getRefreshKey(state: PagingState<Int, BasicMovie>): Int? {
        return state.anchorPosition
    }
    override suspend fun load(params: LoadParams<Int>): PagingSource.LoadResult<Int, BasicMovie> {
        val currentIndex = params.key ?: 1
        val pageItems = movieLoader(currentIndex)
        
        return when(pageItems){
            is NetworkResponse.Success -> {
                Log.d(TAG, "load: items in page = ${pageItems.body.results.size}, page = ${pageItems.body.page}, totalPages = ${pageItems.body.totalPages}")
                val mappedItems = pageItems.body.results.map { BasicMovie.fromApiResponse(it) }
                
                val nextPage = if(currentIndex >= pageItems.body.totalPages) null else ++pageIndex
                LoadResult.Page(mappedItems, null, nextPage)
            }
            is NetworkResponse.NetworkError ->{
                Log.e(TAG, "load: network error", pageItems.error)
                LoadResult.Error(pageItems.error)
            }
            is NetworkResponse.ServerError ->{
                val throwable = Exception("Failed to call API. Error code=${pageItems.code}")
                Log.e(TAG, "load: Server Error", throwable)
                LoadResult.Error(throwable)
            }
            is NetworkResponse.UnknownError ->{
                val throwable = Exception("Unknown Error. Error code=${pageItems.code}", pageItems.error)
                Log.e(TAG, "load: Unknown error", throwable)
                LoadResult.Error(throwable)
            }
        }
    }
}