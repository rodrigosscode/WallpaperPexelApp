package br.com.sscode.wallpaperpexelapp.framework.db.dao

import androidx.room.*
import br.com.sscode.core.data.DbConstants
import br.com.sscode.wallpaperpexelapp.framework.db.entity.PhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WallpapersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: PhotoEntity)

    @Query("SELECT * FROM ${DbConstants.PHOTO_TABLE_NAME}")
    suspend fun getAllPhotos(): Flow<List<PhotoEntity>>

    @Query("DELETE FROM ${DbConstants.PHOTO_TABLE_NAME} WHERE id = :id")
    suspend fun deleteById(id: Int)
}