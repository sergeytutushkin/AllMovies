package dev.tutushkin.allmovies.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.tutushkin.allmovies.data.core.db.MoviesDb
import dev.tutushkin.allmovies.data.movies.local.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideMoviesDb(
        @ApplicationContext appContext: Context
    ): MoviesDb = MoviesDb.getDatabase(appContext)

    @Provides
    fun provideMoviesDao(
        moviesDb: MoviesDb
    ): MoviesDao = moviesDb.moviesDao()

    @Provides
    fun provideMovieDetailsDao(
        moviesDb: MoviesDb
    ): MovieDetailsDao = moviesDb.movieDetailsDao()

    @Provides
    fun provideGenresDao(
        moviesDb: MoviesDb
    ): GenresDao = moviesDb.genresDao()

    @Provides
    fun provideActorsDao(
        moviesDb: MoviesDb
    ): ActorsDao = moviesDb.actorsDao()

    @Provides
    fun provideConfigurationDao(
        moviesDb: MoviesDb
    ): ConfigurationDao = moviesDb.configurationDao()
}
