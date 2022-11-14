package il.co.procyonapps.bitmovie.api

import com.haroldadmin.cnradapter.NetworkResponse
import il.co.procyonapps.bitmovie.api.responses.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApi {
    
    @GET("/3/movie/top_rated")
    fun getUpcomingMovies(@Query("page") page:Int): SimpleNetworkResponse<MovieListResponse>
    
    @GET("/3/movie/now_playing")
    fun getNowPlaying(@Query("page") page:Int): SimpleNetworkResponse<MovieListResponse>
    
    @GET("/3/movie/top_rated")
    fun getTopRated(@Query("page") page:Int): SimpleNetworkResponse<MovieListResponse>
    
    
}