package br.com.sscode.wallpaperpexelapp.framework.local

import br.com.sscode.core.data.dbrepository.GalleryLocalDataSource
import br.com.sscode.core.model.PhotoDomain
import br.com.sscode.wallpaperpexelapp.framework.db.dao.WallpapersDao
import br.com.sscode.wallpaperpexelapp.framework.db.entity.PhotoEntity
import br.com.sscode.wallpaperpexelapp.framework.db.entity.toPhotoDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GalleryLocalDataSourceImpl @Inject constructor(
    private val dao: WallpapersDao
) : GalleryLocalDataSource {

    override suspend fun insert(photoDomain: PhotoDomain) = dao.insert(photoDomain.toEntity())

    override suspend fun get(): Flow<List<PhotoDomain>> = dao.getAll().map {
        it.toPhotoDomain()
    }

    override suspend fun delete(photoDomain: PhotoDomain) = dao.delete(photoDomain.toEntity())

    private fun PhotoDomain.toEntity() = PhotoEntity(
        id = this.id ?: 0,
        photo = this.srcDomain?.original ?: "",
        smallPhoto = this.srcDomain?.small ?: "",
        photographer = this.photographer ?: "",
        avgColor = this.avgColor ?: ""
    )
}