package br.com.sscode.core.usecase.insertgalleryusecase

import br.com.sscode.core.data.dbrepository.GalleryRepository
import br.com.sscode.core.usecase.base.CoroutinesDispatchers
import br.com.sscode.core.usecase.base.ResultStatus
import br.com.sscode.core.usecase.base.UseCase
import kotlinx.coroutines.withContext
import javax.inject.Inject

class InsertGalleryUseCaseImpl @Inject constructor(
    private val repository: GalleryRepository, private val dispatchers: CoroutinesDispatchers
) : UseCase<InsertGalleryUseCase.Params, Unit>(), InsertGalleryUseCase {
    override suspend fun doWork(params: InsertGalleryUseCase.Params): ResultStatus<Unit> {
        return withContext(dispatchers.io()) {
            repository.insert(params.photoDomain)
            ResultStatus.Success(Unit)
        }
    }
}