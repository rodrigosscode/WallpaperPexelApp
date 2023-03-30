package br.com.sscode.core.usecase.getgalleryusecase

import br.com.sscode.core.data.dbrepository.GalleryRepository
import br.com.sscode.core.model.PhotoDomain
import br.com.sscode.core.usecase.base.CoroutinesDispatchers
import br.com.sscode.core.usecase.base.FlowUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetGalleryUseCaseImpl @Inject constructor(
    private val repository: GalleryRepository,
    private val dispatcher: CoroutinesDispatchers
) : FlowUseCase<Unit, List<PhotoDomain>>(), GetGalleryUseCase {

    override suspend fun createFlowObservable(params: Unit): Flow<List<PhotoDomain>> {
        return withContext(dispatcher.io()) {
            repository.get()
        }
    }
}