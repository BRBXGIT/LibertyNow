package com.example.data.data

import com.example.data.domain.AuthRepo
import com.example.local.datastore.auth.AuthManager
import com.example.local.datastore.auth.LoggingState
import com.example.network.auth.api.AuthApiInstance
import com.example.network.auth.models.auth_body_request.AuthBodyRequest
import com.example.network.auth.models.session_token_response.SessionTokenResponse
import com.example.network.common.utils.NetworkErrors
import com.example.network.common.utils.NetworkResponse
import com.example.network.common.utils.processNetworkErrors
import com.example.network.common.utils.processNetworkErrorsForUi
import com.example.network.common.utils.processNetworkExceptions
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
    ): NetworkResponse<SessionTokenResponse> {
        return try {
            val response = authApiInstance.getSessionToken(
                AuthBodyRequest(
                    login = email,
                    password = password
                )
            )

            if (response.code() == 200) {
                val error = response.body()?.error
                if (error == "Не удалось авторизоваться. Неправильные логин/пароль") {
                    NetworkResponse(
                        response = response.body(),
                        error = NetworkErrors.INCORRECT_EMAIL_OR_PASSWORD
                    )
                } else {
                    NetworkResponse(
                        response = response.body(),
                        error = NetworkErrors.SUCCESS
                    )
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
            val error = processNetworkExceptions(e)
            val label = processNetworkErrorsForUi(error)
            NetworkResponse(
                response = null,
                error = error,
                label = label
            )
        }
    }
}