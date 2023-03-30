package br.com.sscode.wallpaperpexelapp.ui.fragment.gallery.viewmodel

import androidx.lifecycle.*
import br.com.sscode.core.model.PhotoDomain
import br.com.sscode.core.usecase.base.CoroutinesDispatchers
import br.com.sscode.core.usecase.deletegalleryusecase.DeleteFavoriteUseCase
import br.com.sscode.core.usecase.getgalleryusecase.GetGalleryUseCase
import br.com.sscode.wallpaperpexelapp.ui.extension.watchStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val getGalleryUseCase: GetGalleryUseCase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase,
    private val dispatcher: CoroutinesDispatchers
) : ViewModel() {

    private val action = MutableLiveData<Action>()

    val state: LiveData<UiState> = action.switchMap { action ->
        liveData(dispatcher.main()) {
            when (action) {
                is Action.GetGallery -> {
                    getPhotos()
                }
                is Action.DeleteFavorite -> {
                    deletePhoto(action)
                }
            }
        }
    }

    init {
        getGallery()
    }

    private fun getGallery() {
        action.value = Action.GetGallery
    }

    fun delete(photoDomain: PhotoDomain) {
        action.value = Action.DeleteFavorite(photoDomain)
    }

    private suspend fun LiveDataScope<UiState>.getPhotos() {
        getGalleryUseCase().catch {
            emit(UiState.Error)
        }.collect {
            val uiState = if (it.isEmpty()) {
                UiState.EmptyGallery
            } else {
                UiState.ShowGallery(it)
            }

            emit(uiState)
        }
    }

    private suspend fun LiveDataScope<UiState>.deletePhoto(action: Action.DeleteFavorite) {
        deleteFavoriteUseCase(DeleteFavoriteUseCase.Params(action.photoDomain)).watchStatus(
            loading = {},
            success = {
                getGallery()
            }, error = {
                emit(UiState.Error)
            }
        )
    }

    sealed class Action {
        object GetGallery : Action()
        data class DeleteFavorite(val photoDomain: PhotoDomain) : Action()
    }

    sealed class UiState {
        data class ShowGallery(val photos: List<PhotoDomain>) : UiState()
        object EmptyGallery : UiState()
        object Error : UiState()
    }
}