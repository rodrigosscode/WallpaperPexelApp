package br.com.sscode.core.usecase.popularusecase

import androidx.paging.Pager
import androidx.paging.PagingData
import br.com.sscode.core.data.PopularRepository
import br.com.sscode.core.model.PhotoDomain
import br.com.sscode.core.usecase.base.PagingUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPopularUseCaseImpl @Inject constructor(
    private val repository: PopularRepository
) : PagingUseCase<GetPopularUseCase.GetPopularParams, PhotoDomain>(), GetPopularUseCase {

    override fun createFlowObservable(params: GetPopularUseCase.GetPopularParams): Flow<PagingData<PhotoDomain>> {
        val fetchPopular = repository.fetchPopular(pages = params.pagingConfig.pageSize)
        return Pager(config = params.pagingConfig) {
            fetchPopular
        }.flow
    }
}