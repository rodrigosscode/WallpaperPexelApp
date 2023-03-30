package br.com.sscode.wallpaperpexelapp.framework.di

import br.com.sscode.core.data.dbrepository.GalleryLocalDataSource
import br.com.sscode.core.data.dbrepository.GalleryRepository
import br.com.sscode.wallpaperpexelapp.framework.db.repository.GalleryRepositoryImpl
import br.com.sscode.wallpaperpexelapp.framework.local.GalleryLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface GalleryRepositoryModule {

    @Binds
    fun bindGalleryRepository(galleryRepositoryImpl: GalleryRepositoryImpl): GalleryRepository

    @Binds
    fun bindLocalDataSource(galleryLocalDataSourceImpl: GalleryLocalDataSourceImpl): GalleryLocalDataSource

}