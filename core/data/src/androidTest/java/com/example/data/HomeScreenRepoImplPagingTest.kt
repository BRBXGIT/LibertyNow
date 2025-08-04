package com.example.data

import androidx.paging.AsyncPagingDataDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.example.data.data.HomeScreenRepoImpl
import com.example.data.domain.HomeScreenRepo
import com.example.network.common.models.anime_list_with_pagination_response.AnimeListWithPaginationResponse
import com.example.network.common.models.anime_list_with_pagination_response.Data
import com.example.network.common.models.anime_list_with_pagination_response.Meta
import com.example.network.common.models.anime_list_with_pagination_response.Pagination
import com.example.network.common.models.common.Name
import com.example.network.home_screen.api.HomeScreenApiInstance
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class HomeScreenRepoImplPagingTest {

    private val api = mockk<HomeScreenApiInstance>()
    private lateinit var repo: HomeScreenRepo

    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repo = HomeScreenRepoImpl(api)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getTitlesByQueryReturnsCorrectPagingData() = runTest {
        // Given
        val query = "Наруто"
        val mockDataList = listOf(
            Data(id = 1, name = Name(main = "Наруто")),
            Data(id = 2, name = Name(main = "Наруто шиппуден"))
        )
        val mockResponse = AnimeListWithPaginationResponse(
            data = mockDataList,
            meta = Meta(Pagination(currentPage = 1))
        )

        coEvery {
            api.getTitlesByQuery(any())
        } returns Response.success(mockResponse)

        val pagingData = repo.getTitlesByQuery(query).first()

        val differ = AsyncPagingDataDiffer(
            diffCallback = DiffCallback(),
            updateCallback = NoopListCallback(),
            workerDispatcher = Dispatchers.Main
        )

        differ.submitData(pagingData)

        advanceUntilIdle()

        // Then
        assertEquals(2, differ.itemCount)
        assertEquals("Наруто", differ.snapshot()[0]?.name?.main)
        assertEquals("Наруто шиппуден", differ.snapshot()[1]?.name?.main)
    }

    // Dummy callback, Paging needs it
    class NoopListCallback : ListUpdateCallback {
        override fun onInserted(position: Int, count: Int) {}
        override fun onRemoved(position: Int, count: Int) {}
        override fun onMoved(fromPosition: Int, toPosition: Int) {}
        override fun onChanged(position: Int, count: Int, payload: Any?) {}
    }

    // DiffUtil.ItemCallback<Data>
    class DiffCallback : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean = oldItem == newItem
    }
}