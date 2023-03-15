package br.com.sscode.wallpaperpexelapp.framework.di

import br.com.sscode.core.data.PopularRepository
import br.com.sscode.core.data.RemoteDataSource
import br.com.sscode.wallpaperpexelapp.framework.network.response.DataWrapperResponse
import br.com.sscode.wallpaperpexelapp.framework.remote.PopularRemoteDataSourceImpl
import br.com.sscode.wallpaperpexelapp.framework.repository.PopularRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindPopularRepository(repository: PopularRepositoryImpl): PopularRepository

    @Binds
    fun bindRemoteDataSource(dataSourceImpl: PopularRemoteDataSourceImpl): RemoteDataSource<DataWrapperResponse>
}