package br.com.sscode.core.usecase.deletegalleryusecase

import br.com.sscode.core.data.dbrepository.GalleryRepository
import br.com.sscode.core.usecase.base.CoroutinesDispatchers
import br.com.sscode.core.usecase.base.ResultStatus
import br.com.sscode.core.usecase.base.UseCase
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteFavoriteUseCaseImpl @Inject constructor(
    private val galleryRepository: GalleryRepository,
    private val dispatchers: CoroutinesDispatchers
) : UseCase<DeleteFavoriteUseCase.Params, Unit>(), DeleteFavoriteUseCase {
    override suspend fun doWork(params: DeleteFavoriteUseCase.Params): ResultStatus<Unit> {
        return withContext(dispatchers.io()) {
            galleryRepository.delete(
                params.photoDomain
            )
            ResultStatus.Success(Unit)
        }
    }
}