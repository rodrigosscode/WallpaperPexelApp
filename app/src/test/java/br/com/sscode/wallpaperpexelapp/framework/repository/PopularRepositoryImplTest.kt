package br.com.sscode.wallpaperpexelapp.framework.repository

import br.com.sscode.core.data.PopularRepository
import br.com.sscode.core.data.RemoteDataSource
import br.com.sscode.testing.MainCoroutineTestRule
import br.com.sscode.wallpaperpexelapp.framework.network.response.DataWrapperResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class PopularRepositoryImplTest {

    @get:Rule
    val mainCoroutineTestRule = MainCoroutineTestRule()

    @Mock
    lateinit var remoteDataSource: RemoteDataSource<DataWrapperResponse>

    private lateinit var subject: PopularRepository

    @Before
    fun setup() {
        subject = PopularRepositoryImpl(remoteDataSource)
    }

    @Test
    fun `should return paging source when fetchPopular`() = runTest {

        // Act
        val result = subject.fetchPopular(pages = PER_PAGE_SIZE)

        // Assert
        assertNotNull(result)
    }

    companion object {
        private const val PER_PAGE_SIZE = 1
    }
}