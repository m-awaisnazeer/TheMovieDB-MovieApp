package com.example.themoviedb.common.di

import android.content.Context
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.themoviedb.common.data.MovieRepositoryImp
import com.example.themoviedb.common.data.api.TheMovieDbApi
import com.example.themoviedb.common.data.cache.MovieDatabase
import com.example.themoviedb.common.data.cache.dao.MovieDao
import com.example.themoviedb.common.data.cache.dao.RemoteKeysDao
import com.example.themoviedb.common.domain.repositories.MovieRepository
import com.example.themoviedb.common.utils.Constants
import com.example.themoviedb.common.utils.DefaultDispatcher
import com.example.themoviedb.common.utils.DispatcherProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieModule {
    companion object{
        @Provides
        @Singleton
        fun movieDatabase(@ApplicationContext appContext: Context): MovieDatabase {
            return Room.databaseBuilder(
                appContext,
                MovieDatabase::class.java,
                "movie.db"
            ).build()
        }

        @Provides
        fun remoteKeysDao(database: MovieDatabase): RemoteKeysDao {
            return database.remoteKeysDao()
        }

        @Provides
        fun movieDao(database: MovieDatabase): MovieDao {
            return database.movieDao()
        }

        @Provides
        @Singleton
        fun bindDispatcher(): DispatcherProvider = DefaultDispatcher()

        @Provides
        @Singleton
        fun httpClient(@ApplicationContext context: Context)= OkHttpClient.Builder()
            .addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .collector(ChuckerCollector(context))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(true)
                    .build()
            )
            .build()

        @Provides
        @Singleton
        fun bindApi(client: OkHttpClient) = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(Constants.BASE_URL)
            .build().create(TheMovieDbApi::class.java)
    }

    @Binds
    @Singleton
    abstract fun bindMovieRepository(repositoryImpl: MovieRepositoryImp):MovieRepository
}