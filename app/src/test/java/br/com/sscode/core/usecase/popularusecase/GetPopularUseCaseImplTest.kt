package br.com.sscode.core.usecase.popularusecase

import androidx.paging.PagingConfig
import br.com.sscode.core.data.PopularRepository
import br.com.sscode.testing.MainCoroutineTestRule
import br.com.sscode.testing.model.WallpapersFactory
import br.com.sscode.testing.pagingsource.PagingSourceFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetPopularUseCaseImplTest {

    @get:Rule
    val mainCoroutineTestRule = MainCoroutineTestRule()

    @Mock
    lateinit var popularRepository: PopularRepository

    private lateinit var getPopularUseCase: GetPopularUseCase

    private val photos = WallpapersFactory().create(WallpapersFactory.Photo.PhotoDomainSuccess)
    private val mockPagingSource = PagingSourceFactory().create(listOf(photos))

    @Before
    fun setup() {
        getPopularUseCase = GetPopularUseCaseImpl(popularRepository)
    }

    @Test
    fun `should validate flow paging data creation when invoke from use case is called`() =
        runTest {

            whenever(popularRepository.fetchPopular(40)).thenReturn(mockPagingSource)

            val result =
                getPopularUseCase(GetPopularUseCase.GetPopularParams(PagingConfig(pageSize = 40)))

            verify(popularRepository).fetchPopular(40)

            assertNotNull(result)
        }
}