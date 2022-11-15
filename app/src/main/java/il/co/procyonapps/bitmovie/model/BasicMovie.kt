package il.co.procyonapps.bitmovie.model

import il.co.procyonapps.bitmovie.api.responses.MovieListResponse

data class BasicMovie(val id: Int, val title: String, val posterUrl: String, var isFavorite: Boolean = false){
    companion object{
        fun fromApiResponse(schema: MovieListResponse.Result): BasicMovie{
            return BasicMovie(schema.id, schema.title, "https://image.tmdb.org/t/p/w92${schema.posterPath}")
        }
    }
}
