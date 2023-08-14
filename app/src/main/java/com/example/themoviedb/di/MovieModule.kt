package com.example.themoviedb.di

import android.content.Context
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.applications.data.MovieRepositoryImp
import com.applications.network.TheMovieDbApi
import com.applications.database.MovieDatabase
import com.applications.database.dao.MovieDao
import com.applications.database.dao.RemoteKeysDao
import com.applications.domain.repositories.MovieRepository
import com.applications.utils.Constants
import com.applications.utils.DefaultDispatcher
import com.applications.utils.DispatcherProvider
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
        fun bindDispatcher(): DispatcherProvider =
            DefaultDispatcher()

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
    abstract fun bindMovieRepository(repositoryImpl: MovieRepositoryImp): MovieRepository
}