package br.com.sscode.wallpaperpexelapp.framework.paging

import androidx.paging.PagingSource
import br.com.sscode.core.data.RemoteDataSource
import br.com.sscode.testing.MainCoroutineTestRule
import br.com.sscode.wallpaperpexelapp.factory.DataWrapperResponseFactory
import br.com.sscode.wallpaperpexelapp.framework.network.response.DataWrapperResponse
import br.com.sscode.wallpaperpexelapp.framework.network.response.toPhotoDomain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PopularPagingSourceTest {

    @get:Rule
    val mainCoroutineTestRule = MainCoroutineTestRule()

    @Mock
    lateinit var remoteDataSource: RemoteDataSource<DataWrapperResponse>

    private val popularResponse = DataWrapperResponseFactory().create(
        nextPage = NEXT_PAGE_KEY,
        page = PAGE_ONE,
        perPage = PER_PAGE_SIZE
    )

    private lateinit var subject: PopularPagingSource

    @Before
    fun setup() {
        subject = PopularPagingSource(dataSource = remoteDataSource, pages = PER_PAGE_SIZE)
    }

    @Test
    fun `should return page result when page load with valid response`() = runTest {

        // Arrange
        val parameters = PagingSource.LoadParams.Prepend(
            key = PAGE_ONE,
            loadSize = PER_PAGE_SIZE,
            placeholdersEnabled = PLACEHOLDERS_ENABLED
        )

        val expected = PagingSource.LoadResult.Page(
            data = popularResponse.photos.map { it.toPhotoDomain() },
            prevKey = null,
            nextKey = NEXT_PAGE_KEY
        )

        whenever(remoteDataSource.fetchPopular(page = PAGE_ONE, perPage = PER_PAGE_SIZE))
            .thenReturn(popularResponse)

        // Act
        val result = subject.load(parameters)

        // Assert
        assertNotNull(result)
        assertTrue(result is PagingSource.LoadResult.Page)
        assertEquals(expected, result)
    }

    @Test
    fun `should return page error when page load with exception`() = runTest {

        // Arrange
        val parameters = PagingSource.LoadParams.Prepend(
            key = PAGE_ONE,
            loadSize = PER_PAGE_SIZE,
            placeholdersEnabled = PLACEHOLDERS_ENABLED
        )

        whenever(remoteDataSource.fetchPopular(page = PAGE_ONE, perPage = PER_PAGE_SIZE))
            .thenThrow(RuntimeException())

        // Act
        val result = subject.load(parameters)

        // Assert
        assertNotNull(result)
        assertTrue(result is PagingSource.LoadResult.Error)
    }

    companion object {
        private const val PER_PAGE_SIZE = 1
        private const val PAGE_ONE = 1
        private const val NEXT_PAGE_KEY = 2
        private const val PLACEHOLDERS_ENABLED = false
    }
}