package xyz.c3rberuss.movies.usecases.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import xyz.c3rberuss.movies.data.MoviesRepository
import xyz.c3rberuss.movies.usecases.*

@Module
@InstallIn(ViewModelComponent::class)
class UseCasesModule {

    @Provides
    @ViewModelScoped
    fun providesAddToFavoritesMoviesUseCase(repository: MoviesRepository) =
        AddToFavoritesMoviesUseCase(repository)

    @Provides
    @ViewModelScoped
    fun providesAddToNoFavoritesMoviesUseCase(repository: MoviesRepository) =
        AddToNoFavoritesMoviesUseCase(repository)

    @Provides
    @ViewModelScoped
    fun providesFetchFavoritesMoviesUseCase(repository: MoviesRepository) =
        FetchFavoritesMoviesUseCase(repository)

    @Provides
    @ViewModelScoped
    fun providesFetchMovieUseCase(repository: MoviesRepository) =
        FetchMovieUseCase(repository)

    @Provides
    @ViewModelScoped
    fun providesFetchNoFavoritesMoviesUseCase(repository: MoviesRepository) =
        FetchNoFavoritesMoviesUseCase(repository)

    @Provides
    @ViewModelScoped
    fun providesFetchPopularMoviesUseCase(repository: MoviesRepository) =
        FetchPopularMoviesUseCase(repository)

    @Provides
    @ViewModelScoped
    fun providesIsFavoriteMovieUseCase(repository: MoviesRepository) =
        IsFavoriteMovieUseCase(repository)

    @Provides
    @ViewModelScoped
    fun providesIsNoFavoriteMovieUseCase(repository: MoviesRepository) =
        IsNoFavoriteMovieUseCase(repository)

    @Provides
    @ViewModelScoped
    fun providesRemoveFromFavoritesUseCase(repository: MoviesRepository) =
        RemoveFromFavoritesUseCase(repository)

    @Provides
    @ViewModelScoped
    fun providesRemoveFromNoFavoritesUseCase(repository: MoviesRepository) =
        RemoveFromNoFavoritesUseCase(repository)

}