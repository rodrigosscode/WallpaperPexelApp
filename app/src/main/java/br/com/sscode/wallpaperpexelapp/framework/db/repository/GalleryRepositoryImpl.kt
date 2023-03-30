package br.com.sscode.wallpaperpexelapp.framework.db.repository

import br.com.sscode.core.data.dbrepository.GalleryLocalDataSource
import br.com.sscode.core.data.dbrepository.GalleryRepository
import br.com.sscode.core.model.PhotoDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GalleryRepositoryImpl @Inject constructor(
    private val dataSource: GalleryLocalDataSource
) : GalleryRepository {

    override suspend fun insert(photoDomain: PhotoDomain) = dataSource.insert(photoDomain)

    override suspend fun get(): Flow<List<PhotoDomain>> = dataSource.get()

    override suspend fun delete(photoDomain: PhotoDomain) = dataSource.delete(photoDomain)
}