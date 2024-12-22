package id.co.mondo.storyapp.ui.story

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import id.co.mondo.storyapp.data.local.StoryDatabase
import id.co.mondo.storyapp.data.StoryRemoteMediator
import id.co.mondo.storyapp.data.network.response.FileUploadResponse
import id.co.mondo.storyapp.data.network.response.ListStoryItem
import id.co.mondo.storyapp.data.network.retrofit.ApiConfig
import id.co.mondo.storyapp.data.network.retrofit.ApiService
import id.co.mondo.storyapp.ui.utils.UserPreferences
import kotlinx.coroutines.flow.firstOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody

open class StoryRepository(
    private val api: ApiService,
    private val userPreferences: UserPreferences,
    private val storyDatabase: StoryDatabase
    ) {

    companion object {
        @Volatile
        private var INSTANCE: StoryRepository? = null

        fun getInstance(api: ApiService, userPreferences: UserPreferences, storyDatabase: StoryDatabase): StoryRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = StoryRepository(api, userPreferences, storyDatabase)
                INSTANCE = instance
                instance
            }
        }
    }

    suspend fun getStories(location: Int = 0): Result<List<ListStoryItem>> {
        return try {
            val token = userPreferences.token.firstOrNull()
            Log.d("StoryRepository", "Menggunakan token: $token")
            if (token.isNullOrEmpty()) {
                Log.e("StoryRepository", "Token tidak ditemukan")
                return Result.failure(Exception("Token tidak ditemukan"))
            }
            val response = api.getAllStories(ApiConfig.getAuthHeader(token),location = location)
            Log.d("StoryRepository", "Respons server: ${response.message}")
            if (response.listStory.isNotEmpty()) {
                Log.d("StoryRepository", "Berhasil mendapatkan cerita: ${response.listStory.size}")
                Result.success(response.listStory)
            } else {
                Log.d("StoryRepository", "Daftar cerita kosong")
                Result.failure(Exception("Daftar Kosong"))
            }
        } catch (e: Exception) {
            Log.e("StoryRepository", "getStrories() - Error: ${e.message}")
            Result.failure(Exception("Gagal mengunggah cerita. Penyebab: ${e.message ?: "Kesalahan tidak diketahui"}", e))
        }
    }

    fun getPagedStories(): LiveData<PagingData<ListStoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, api, userPreferences),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStory()
            }
        ).liveData
    }

    suspend fun uploadStory(photo: MultipartBody.Part, description: RequestBody): Result<FileUploadResponse> {
        return try {
            val token = userPreferences.token.firstOrNull()
                ?: return Result.failure(Exception("Token tidak ditemukan"))

            Log.d("Token", "Token: $token") // Debug token

            val response = api.uploadStory(ApiConfig.getAuthHeader(token) ,photo, description)
            if (response.error) {
                Result.failure(Exception(response.message))
            } else {
                Result.success(response)
            }
        } catch (e: Exception) {
            Log.e("UploadStoryError", "Error: ${e.localizedMessage}")
            Result.failure(Exception("Gagal mengunggah: ${e.localizedMessage}", e))
        }
    }

    suspend fun clearToken() {
        userPreferences.clearToken()
    }
}
