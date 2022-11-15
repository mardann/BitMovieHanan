package il.co.procyonapps.bitmovie.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import il.co.procyonapps.bitmovie.api.responses.MovieListResponse
import il.co.procyonapps.bitmovie.db.FavoriteMovieEntity

data class BasicMovie(val id: Int, val title: String, val posterUrl: String, var isFavorite: MutableLiveData<Boolean> = MutableLiveData(false)){
    companion object{
        fun fromApiResponse(schema: MovieListResponse.Result): BasicMovie{
            return BasicMovie(schema.id, schema.title, "https://image.tmdb.org/t/p/w92${schema.posterPath}")
        }
        
        fun fromEntity(schema: FavoriteMovieEntity): BasicMovie{
            return BasicMovie(
                schema.id,
                schema.movieName,
                schema.posterUrl,
                MutableLiveData(true)
            )
        }
    }
    
    fun toEntity(): FavoriteMovieEntity = FavoriteMovieEntity(this.id, this.title, this.posterUrl)
}
