package il.co.procyonapps.bitmovie.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteMovieEntity::class], version = 1)
abstract class MoviesDatabase: RoomDatabase() {
    abstract fun dao(): FavoriteMoviesDao
}