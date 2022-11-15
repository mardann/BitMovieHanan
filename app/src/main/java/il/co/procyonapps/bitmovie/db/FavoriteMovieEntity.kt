package il.co.procyonapps.bitmovie.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movies_table")
class FavoriteMovieEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "movieName") val movieName: String,
    @ColumnInfo(name = "posterUrl") val posterUrl: String
)
