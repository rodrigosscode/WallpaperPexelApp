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
    fun getAll(): Flow<List<PhotoEntity>>

    @Delete
    suspend fun delete(entity: PhotoEntity)

    @Query("SELECT * FROM ${DbConstants.PHOTO_TABLE_NAME} ORDER BY RANDOM() LIMIT 1")
    suspend fun getRandomWallpaper(): PhotoEntity
}