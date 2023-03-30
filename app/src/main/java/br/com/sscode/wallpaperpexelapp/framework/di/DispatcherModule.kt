package br.com.sscode.wallpaperpexelapp.framework.di

import br.com.sscode.core.usecase.base.AppCoroutinesDispatcher
import br.com.sscode.core.usecase.base.CoroutinesDispatchers
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DispatcherModule {

    @Binds
    fun bindDispatcher(appCoroutinesDispatcher: AppCoroutinesDispatcher): CoroutinesDispatchers
}