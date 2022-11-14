package il.co.procyonapps.bitmovie.di

import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import il.co.procyonapps.bitmovie.api.APIConst
import il.co.procyonapps.bitmovie.api.TmdbApi
import il.co.procyonapps.bitmovie.api.responses.AuthorizationInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkProviderModule {
    @Singleton
    @Provides
    fun provideHttpClient(authInterceptor: AuthorizationInterceptor):
            OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }
    
    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .build()
    }
    
    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient, moshi: Moshi): Retrofit{
        return Retrofit.Builder()
            .baseUrl(APIConst.BASE_URL)
            .client(httpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .build()
    }
    
    @Singleton
    @Provides
    fun provideTmdbApi(retrofit: Retrofit): TmdbApi = retrofit.create(TmdbApi::class.java)
}