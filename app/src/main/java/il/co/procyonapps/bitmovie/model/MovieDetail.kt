package il.co.procyonapps.bitmovie.model

import il.co.procyonapps.bitmovie.api.responses.MovieDetailResponse

data class MovieDetail(val id: Int, val title: String, val posterUrl: String, val rating: Float, val yearOfRelease: String, val description: String){
    
    companion object{
        fun fromApi(schema: MovieDetailResponse):MovieDetail{
            return MovieDetail(
                schema.id,
                schema.title,
                "https://image.tmdb.org/t/p/w342${schema.posterPath}",
                schema.voteAverage.toFloat(),
                schema.releaseDate,
                schema.overview
                
            )
        }
    }

}
