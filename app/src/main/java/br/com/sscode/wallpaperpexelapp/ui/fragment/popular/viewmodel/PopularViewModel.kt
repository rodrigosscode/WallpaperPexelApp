package br.com.sscode.wallpaperpexelapp.ui.fragment.popular.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.sscode.core.model.PhotoDomain
import br.com.sscode.core.usecase.popularusecase.GetPopularUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val getPopularUseCase: GetPopularUseCase
) : ViewModel() {

    fun popularWallpapers(): Flow<PagingData<PhotoDomain>> {
        return getPopularUseCase(
            params = GetPopularUseCase.GetPopularParams(
                pagingConfig = pagingConfig()
            )
        ).catch {
            emit(PagingData.empty())
        }.cachedIn(viewModelScope)
    }

    private fun pagingConfig() = PagingConfig(pageSize = 40)
}