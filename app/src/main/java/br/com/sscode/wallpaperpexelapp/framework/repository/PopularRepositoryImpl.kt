package br.com.sscode.wallpaperpexelapp.framework.repository

import androidx.paging.PagingSource
import br.com.sscode.core.data.PopularRepository
import br.com.sscode.core.data.RemoteDataSource
import br.com.sscode.core.model.PhotoDomain
import br.com.sscode.wallpaperpexelapp.framework.network.response.DataWrapperResponse
import br.com.sscode.wallpaperpexelapp.framework.paging.PopularPagingSource
import javax.inject.Inject

class PopularRepositoryImpl @Inject constructor(
   private val remoteDataSource: RemoteDataSource<DataWrapperResponse>
): PopularRepository {

    override fun fetchPopular(pages: Int): PagingSource<Int, PhotoDomain> {
        return PopularPagingSource(remoteDataSource, pages)
    }
}