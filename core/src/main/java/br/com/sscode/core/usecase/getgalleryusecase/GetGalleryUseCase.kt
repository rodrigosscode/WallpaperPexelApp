package br.com.sscode.core.usecase.getgalleryusecase

import br.com.sscode.core.model.PhotoDomain
import kotlinx.coroutines.flow.Flow

interface GetGalleryUseCase {
    suspend operator fun invoke(params: Unit = Unit): Flow<List<PhotoDomain>>
}