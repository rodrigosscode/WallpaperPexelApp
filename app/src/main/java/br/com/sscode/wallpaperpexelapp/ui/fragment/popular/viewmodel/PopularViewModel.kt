package br.com.sscode.wallpaperpexelapp.ui.fragment.popular.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import br.com.sscode.core.model.PhotoDomain
import br.com.sscode.core.usecase.insertgalleryusecase.InsertGalleryUseCase
import br.com.sscode.core.usecase.popularusecase.GetPopularUseCase
import br.com.sscode.wallpaperpexelapp.ui.extension.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val getPopularUseCase: GetPopularUseCase,
    private val insertGalleryUseCase: InsertGalleryUseCase
) : ViewModel() {

    private val _favoriteUiState = MutableLiveData<FavoriteUiState>()
    val favoriteUiState: LiveData<FavoriteUiState> get() = _favoriteUiState

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

    fun favoritePhoto(photoDomain: PhotoDomain) = viewModelScope.launch {
        photoDomain.run {
            insertGalleryUseCase(params = InsertGalleryUseCase.Params(photoDomain = this))
                .watchStatus(
                    loading = {
                        _favoriteUiState.value = FavoriteUiState.Loading
                    },
                    success = {
                        _favoriteUiState.value = FavoriteUiState.FavoritePhoto(saved = true)
                    },
                    error = {
                        _favoriteUiState.value = FavoriteUiState.FavoritePhoto(saved = false)
                    }
                )
        }
    }

    sealed class FavoriteUiState {
        object Loading : FavoriteUiState()
        data class FavoritePhoto(val saved: Boolean) : FavoriteUiState()
    }
}