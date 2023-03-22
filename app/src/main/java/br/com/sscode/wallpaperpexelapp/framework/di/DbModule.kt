package br.com.sscode.wallpaperpexelapp.framework.di

import android.content.Context
import androidx.room.Room
import br.com.sscode.core.data.DbConstants
import br.com.sscode.wallpaperpexelapp.framework.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, AppDatabase::class.java, DbConstants.APP_DATABASE_NAME
    ).build()

    @Provides
    fun provideWallpapersDao(db: AppDatabase) = db.wallpapersDao()
}