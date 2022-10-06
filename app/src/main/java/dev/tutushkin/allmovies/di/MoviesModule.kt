package dev.tutushkin.allmovies.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.tutushkin.allmovies.data.movies.MoviesRepositoryImpl
import dev.tutushkin.allmovies.data.movies.local.MoviesLocalDataSource
import dev.tutushkin.allmovies.data.movies.local.MoviesLocalDataSourceImpl
import dev.tutushkin.allmovies.data.movies.remote.MoviesRemoteDataSource
import dev.tutushkin.allmovies.data.movies.remote.MoviesRemoteDataSourceImpl
import dev.tutushkin.allmovies.domain.movies.MoviesRepository

@Module
@InstallIn(SingletonComponent::class)
interface MoviesModule {

    @Binds
    fun bindMoviesRepository(
        impl: MoviesRepositoryImpl
    ): MoviesRepository

    @Binds
    fun bindMoviesLocalDataSource(
        impl: MoviesLocalDataSourceImpl
    ): MoviesLocalDataSource

    @Binds
    fun bindMoviesRemoteDataSource(
        impl: MoviesRemoteDataSourceImpl
    ): MoviesRemoteDataSource
}
