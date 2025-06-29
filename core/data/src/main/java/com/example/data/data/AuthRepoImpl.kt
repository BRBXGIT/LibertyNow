package com.example.data.data

import com.example.common.functions.NetworkErrors
import com.example.common.functions.NetworkResponse
import com.example.common.functions.processNetworkErrors
import com.example.common.functions.processNetworkErrorsForUi
import com.example.common.functions.processNetworkExceptions
import com.example.data.domain.AuthRepo
import com.example.local.datastore.auth.AuthManager
import com.example.local.datastore.auth.AuthState
import com.example.network.auth.api.AuthApiInstance
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    private val authManager: AuthManager,
    private val apiInstance: AuthApiInstance
): AuthRepo {

    override val userSessionToken = authManager.userSessionTokenFlow

    override val authState = userSessionToken.map { token ->
        if (token.isNullOrBlank()) AuthState.LoggedOut else AuthState.LoggedIn
    }

    override suspend fun saveUserSessionToken(token: String) {
        authManager.saveUserSessionToken(token)
    }

    override suspend fun clearUserSessionToken() {
        authManager.clearUserSessionToken()
    }

    override suspend fun getSessionToken(
        email: String,
        password: String
    ): NetworkResponse {
        return try {
            val response = apiInstance.getSessionToken(email, password)

            if (response.code() == 200) {
                NetworkResponse(
                    response = response.body(),
                    error = NetworkErrors.SUCCESS
                )
            } else {
                val error = processNetworkErrors(response.code())
                val label = processNetworkErrorsForUi(error)
                NetworkResponse(
                    error = error,
                    label = label
                )
            }
        } catch (e: Exception) {
            processNetworkExceptions(e)
        }
    }
}