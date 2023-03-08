package br.com.sscode.wallpaperpexelapp.framework.remote

import br.com.sscode.core.data.RemoteDataSource
import br.com.sscode.wallpaperpexelapp.framework.network.api.WallpaperApi
import br.com.sscode.wallpaperpexelapp.framework.network.response.DataWrapperResponse
import javax.inject.Inject

class PopularRemoteDataSourceImpl @Inject constructor(
    private val api: WallpaperApi
) : RemoteDataSource<DataWrapperResponse> {

    override suspend fun fetchPopular(page: Int, perPage: Int): DataWrapperResponse =
        api.getPopularWallpapers(page, perPage)
}