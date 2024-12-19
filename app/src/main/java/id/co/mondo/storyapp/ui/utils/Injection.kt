package id.co.mondo.storyapp.ui.utils

import android.content.Context
import android.util.Log
import id.co.mondo.storyapp.data.StoryDatabase
import id.co.mondo.storyapp.data.network.retrofit.ApiConfig
import id.co.mondo.storyapp.ui.story.StoryRepository


object Injection {
    suspend fun provideRepository(context: Context): StoryRepository {
        val pref = UserPreferences.getInstance(context)
        val user = pref.getUser()
        Log.d("Injection", "Token ditemukan: ${user.token}")
        val apiService = ApiConfig.getApiService(user.token)
        val database = StoryDatabase.getDatabase(context)
        Log.d("Injection", "ApiService berhasil dibuat dengan token")
        return StoryRepository.getInstance(apiService, pref, database)
    }
}
