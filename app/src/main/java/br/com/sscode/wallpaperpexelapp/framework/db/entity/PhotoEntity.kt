package br.com.sscode.wallpaperpexelapp.framework.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.sscode.core.data.DbConstants
import br.com.sscode.core.model.PhotoDomain
import br.com.sscode.core.model.SrcDomain

@Entity(tableName = DbConstants.PHOTO_TABLE_NAME)
data class PhotoEntity(
    @PrimaryKey val id: Int,
    val photo: String,
    val smallPhoto: String,
    val photographer: String,
    val avgColor: String
)

fun List<PhotoEntity>.toPhotoDomain() = map {
    PhotoDomain(
        id = it.id,
        photographer = it.photographer,
        avgColor = it.avgColor,
        srcDomain = SrcDomain(
            original = it.photo,
            small = it.smallPhoto
        )
    )
}
