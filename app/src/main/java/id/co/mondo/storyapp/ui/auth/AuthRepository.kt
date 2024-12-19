package id.co.mondo.storyapp.ui.auth

import id.co.mondo.storyapp.data.network.response.LoginResponse
import id.co.mondo.storyapp.data.network.response.RegisterResponse
import id.co.mondo.storyapp.data.network.retrofit.ApiService
import id.co.mondo.storyapp.ui.utils.wrapEspressoIdlingResource

class AuthRepository(private val apiService: ApiService) {

    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        val response = apiService.register(name, email, password)
        return if (response.error == true) {
            RegisterResponse(error = true, message = response.message)
        } else {
            response
        }
    }

    suspend fun login(email: String, password: String): LoginResponse {
        return wrapEspressoIdlingResource {
            val response = apiService.login(email, password)
            return if (response.error == true) {
                LoginResponse(
                    error = true,
                    message = response.message,
                    loginResult = response.loginResult
                )
            } else {
                response
            }
        }
    }

}