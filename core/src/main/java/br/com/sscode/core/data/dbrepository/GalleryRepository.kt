package br.com.sscode.core.data.dbrepository

import br.com.sscode.core.model.PhotoDomain
import kotlinx.coroutines.flow.Flow

interface GalleryRepository {
    suspend fun insert(photoDomain: PhotoDomain)
    suspend fun get(): Flow<List<PhotoDomain>>
    suspend fun delete(photoDomain: PhotoDomain)
}