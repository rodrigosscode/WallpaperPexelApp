package br.com.sscode.wallpaperpexelapp.framework.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.sscode.core.data.DbConstants

@Entity(tableName = DbConstants.PHOTO_TABLE_NAME)
data class PhotoEntity(
    @PrimaryKey val id: Int,
    val photo: String
)
