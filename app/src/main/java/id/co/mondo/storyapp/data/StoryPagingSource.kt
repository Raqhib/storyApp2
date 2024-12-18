package id.co.mondo.storyapp.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.co.mondo.storyapp.data.network.response.ListStoryItem
import id.co.mondo.storyapp.data.network.retrofit.ApiService

class StoryPagingSource(private val apiService: ApiService) : PagingSource<Int, ListStoryItem>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, ListStoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListStoryItem> {
        val position = params.key ?: INITIAL_PAGE_INDEX
        return try {
            val response = apiService.getAllStories(page = position, size = params.loadSize)
            val stories = response.listStory ?: emptyList()
            Log.d("StoryPagingSource", "Halaman: $position, Jumlah item: ${stories.size}, Total Load Size: ${params.loadSize}")

            LoadResult.Page(
                data = stories,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (stories.isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}