package il.co.procyonapps.bitmovie.api

import com.haroldadmin.cnradapter.NetworkResponse
import il.co.procyonapps.bitmovie.api.responses.MovieDetailResponse
import il.co.procyonapps.bitmovie.api.responses.MovieListResponse
import il.co.procyonapps.bitmovie.api.responses.VideosResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {
    
    @GET("/3/movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page:Int): NetworkResponse<MovieListResponse, Any>
    
    @GET("/3/movie/now_playing")
    suspend fun getNowPlaying(@Query("page") page:Int): NetworkResponse<MovieListResponse, Any>
    
    @GET("/3/movie/top_rated")
    suspend fun getTopRated(@Query("page") page:Int): NetworkResponse<MovieListResponse, Any>
    
    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") id:String): NetworkResponse<MovieDetailResponse, Any>
    
    @GET("/3/movie/{movie_id}/videos")
    suspend fun getMovieVideos(@Path("movie_id") id: String): NetworkResponse<VideosResult, Any>
    
    
}