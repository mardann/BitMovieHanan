package il.co.procyonapps.bitmovie.api.responses


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieListResponse(
    @Json(name = "dates")
    val dates: Dates? = null,
    @Json(name = "page")
    val page: Int,
    @Json(name = "results")
    val results: List<Result>,
    @Json(name = "total_pages")
    val totalPages: Int,
    @Json(name = "total_results")
    val totalResults: Int
) {
    @JsonClass(generateAdapter = true)
    data class Dates(
        @Json(name = "maximum")
        val maximum: String,
        @Json(name = "minimum")
        val minimum: String
    )

    @JsonClass(generateAdapter = true)
    data class Result(
        @Json(name = "adult")
        val adult: Boolean,
        @Json(name = "backdrop_path", ignore = true)
        val backdropPath: String? = null,
        @Json(name = "genre_ids")
        val genreIds: List<Int>,
        @Json(name = "id")
        val id: Int,
        @Json(name = "original_language")
        val originalLanguage: String,
        @Json(name = "original_title")
        val originalTitle: String,
        @Json(name = "overview")
        val overview: String,
        @Json(name = "popularity")
        val popularity: Double,
        @Json(name = "poster_path")
        val posterPath: String? = null,
        @Json(name = "release_date")
        val releaseDate: String,
        @Json(name = "title")
        val title: String,
        @Json(name = "video")
        val video: Boolean,
        @Json(name = "vote_average")
        val voteAverage: Double,
        @Json(name = "vote_count")
        val voteCount: Int
    )
}