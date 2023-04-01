package br.com.sscode.wallpaperpexelapp.ui.fragment.popular.viewmodel

import androidx.paging.PagingData
import br.com.sscode.core.model.PhotoDomain
import br.com.sscode.core.usecase.insertgalleryusecase.InsertGalleryUseCase
import br.com.sscode.core.usecase.popularusecase.GetPopularUseCase
import br.com.sscode.testing.MainCoroutineTestRule
import br.com.sscode.testing.model.WallpapersFactory
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
internal class PopularViewModelTest {

    @get:Rule
    val mainCoroutineTestRule = MainCoroutineTestRule()

    @Mock
    lateinit var getPopularUseCase: GetPopularUseCase

    @Mock
    lateinit var insertGalleryUseCase: InsertGalleryUseCase

    private lateinit var popularViewModel: PopularViewModel

    @Before
    fun setUp() {
        popularViewModel = PopularViewModel(getPopularUseCase, insertGalleryUseCase)
    }

    @Test
    fun `should validate pagination data`() = runTest {

        whenever(getPopularUseCase(any())).thenReturn(flowOf(getPagingDataMock))

        val result = popularViewModel.popularWallpapers().first()

        assertNotNull(result)
    }

    @Test(expected = RuntimeException::class)
    fun `should return empty paging data when throw exception `() = runTest {
        //Arrange
        whenever(getPopularUseCase(any())).thenThrow(RuntimeException())

        //Act
        popularViewModel.popularWallpapers()
    }

    private val wallpapersFactory = WallpapersFactory()
    private val getPagingDataMock: PagingData<PhotoDomain> = PagingData.from(
        listOf(
            wallpapersFactory.create(WallpapersFactory.Photo.PhotoDomainSuccess),
            wallpapersFactory.create(WallpapersFactory.Photo.PhotoDomainSuccess)
        )
    )
}