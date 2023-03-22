package br.com.sscode.wallpaperpexelapp.framework.db

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.sscode.wallpaperpexelapp.framework.db.dao.WallpapersDao
import br.com.sscode.wallpaperpexelapp.framework.db.entity.PhotoEntity

@Database(entities = [PhotoEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun wallpapersDao(): WallpapersDao
}