package il.co.procyonapps.bitmovie.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
@Dao
abstract class FavoriteMoviesDao {

    @Query("SELECT * FROM favorite_movies_table")
    abstract fun getAllFavoriteMovies(): Flow<List<FavoriteMovieEntity>>
    
    @Query("DELETE FROM favorite_movies_table WHERE id == :id")
    abstract suspend fun deleteMovie(id: Int): Int
    
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertMovie(movie: FavoriteMovieEntity)
}