package il.co.procyonapps.bitmovie.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import il.co.procyonapps.bitmovie.db.MoviesDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBProviderModule {
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): MoviesDatabase = Room .databaseBuilder(
        context,
        MoviesDatabase::class.java,
        "MOVIES_DB"
    ).build()
    
    @Provides
    @Singleton
    fun provideDao(db: MoviesDatabase) = db.dao()
}