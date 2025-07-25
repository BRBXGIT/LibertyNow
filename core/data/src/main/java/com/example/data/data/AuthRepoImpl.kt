package com.example.data.data

import com.example.common.functions.NetworkErrors
import com.example.common.functions.NetworkResponse
import com.example.common.functions.processNetworkErrors
import com.example.common.functions.processNetworkErrorsForUi
import com.example.common.functions.processNetworkExceptions
import com.example.data.domain.AuthRepo
import com.example.local.datastore.auth.AuthManager
import com.example.local.datastore.auth.LoggingState
import com.example.network.auth.api.AuthApiInstance
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    private val authManager: AuthManager,
    private val authApiInstance: AuthApiInstance,
): AuthRepo {

    override val userSessionToken = authManager.userSessionTokenFlow

    override val loggingState = userSessionToken.map { token ->
        if (token.isNullOrBlank()) LoggingState.LoggedOut else LoggingState.LoggedIn
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
            val response = authApiInstance.getSessionToken(email, password)

            if (response.code() == 200) {
                val key = response.body()?.key
                when (key) {
                    "invalidUser" -> {
                        NetworkResponse(
                            response = response.body(),
                            error = NetworkErrors.INCORRECT_EMAIL
                        )
                    }
                    "wrongPasswd" -> {
                        NetworkResponse(
                            response = response.body(),
                            error = NetworkErrors.INCORRECT_PASSWORD
                        )
                    }
                    else -> {
                        NetworkResponse(
                            response = response.body(),
                            error = NetworkErrors.SUCCESS
                        )
                    }
                }
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