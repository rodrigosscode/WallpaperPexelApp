package br.com.sscode.core.usecase.insertgalleryusecase

import br.com.sscode.core.model.PhotoDomain
import br.com.sscode.core.usecase.base.ResultStatus
import kotlinx.coroutines.flow.Flow

interface InsertGalleryUseCase {
    operator fun invoke(params: Params): Flow<ResultStatus<Unit>>
    data class Params(val photoDomain: PhotoDomain)
}