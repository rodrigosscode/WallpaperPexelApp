package br.com.sscode.wallpaperpexelapp.framework.di

import br.com.sscode.core.usecase.deletegalleryusecase.DeleteFavoriteUseCase
import br.com.sscode.core.usecase.deletegalleryusecase.DeleteFavoriteUseCaseImpl
import br.com.sscode.core.usecase.getgalleryusecase.GetGalleryUseCase
import br.com.sscode.core.usecase.getgalleryusecase.GetGalleryUseCaseImpl
import br.com.sscode.core.usecase.insertgalleryusecase.InsertGalleryUseCase
import br.com.sscode.core.usecase.insertgalleryusecase.InsertGalleryUseCaseImpl
import br.com.sscode.core.usecase.popularusecase.GetPopularUseCase
import br.com.sscode.core.usecase.popularusecase.GetPopularUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun bindPopularUseCase(getPopularUseCaseImpl: GetPopularUseCaseImpl): GetPopularUseCase

    @Binds
    fun bindGetGalleryUseCase(getGalleryUseCaseImpl: GetGalleryUseCaseImpl): GetGalleryUseCase

    @Binds
    fun bindDeleteFavoriteUseCase(deleteFavoriteUseCaseImpl: DeleteFavoriteUseCaseImpl): DeleteFavoriteUseCase

    @Binds
    fun bindInsertGalleryUseCase(insertGalleryUseCaseImpl: InsertGalleryUseCaseImpl): InsertGalleryUseCase
}