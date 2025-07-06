package com.example.common.functions

import androidx.paging.PagingSource.LoadResult
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkException(
    val error: NetworkError,
    val label: String
): Exception()

fun processNetworkErrors(statusCode: Int): NetworkError {
    return when (statusCode) {
        in 200..299 -> NetworkErrors.SUCCESS
        401 -> NetworkErrors.UNAUTHORIZED
        408 -> NetworkErrors.REQUEST_TIMEOUT
        409 -> NetworkErrors.CONFLICT
        413 -> NetworkErrors.PAYLOAD_TOO_LARGE
        429 -> NetworkErrors.TOO_MANY_REQUESTS
        in 500..599 -> NetworkErrors.SERVER_ERROR
        else -> NetworkErrors.UNKNOWN
    }
}

fun processNetworkErrorsForUi(error: NetworkError): String {
    return when (error) {
        NetworkErrors.SUCCESS -> "Всё отлично"
        NetworkErrors.UNAUTHORIZED -> "Кажется вы не авторизованы"
        NetworkErrors.REQUEST_TIMEOUT -> "Таймаут апи, попробуйте перезагрузить"
        NetworkErrors.CONFLICT -> "Кажется что-то конфликтует"
        NetworkErrors.PAYLOAD_TOO_LARGE -> "Нагрузка на сервер слишком большая"
        NetworkErrors.TOO_MANY_REQUESTS -> "Слишком много запросов, дайте AniLiberty немного отдохнуть"
        NetworkErrors.SERVER_ERROR -> "Ошибка сервера"
        NetworkErrors.INTERNET -> "Пожалуйста подключитесь к сети :)"
        NetworkErrors.SERIALIZATION -> "Проблема с сериализацией"
        else -> "Неизвестная ошибка, попробуйте с впн"
    }
}

fun <K : Any, V : Any> processNetworkExceptionsForPaging(e: Throwable): LoadResult<K, V> {
    return when (e) {
        is UnknownHostException, is SocketException -> {
            val label = processNetworkErrorsForUi(NetworkErrors.INTERNET)
            LoadResult.Error(NetworkException(NetworkErrors.INTERNET, label))
        }
        is SocketTimeoutException -> {
            val label = processNetworkErrorsForUi(NetworkErrors.REQUEST_TIMEOUT)
            LoadResult.Error(NetworkException(NetworkErrors.REQUEST_TIMEOUT, label))
        }
        else -> {
            val label = processNetworkErrorsForUi(NetworkErrors.UNKNOWN)
            LoadResult.Error(NetworkException(NetworkErrors.UNKNOWN, label))
        }
    }
}

fun processNetworkExceptions(e: Exception): NetworkResponse {
    return when (e) {
        is UnknownHostException -> {
            val label = processNetworkErrorsForUi(NetworkErrors.INTERNET)
            NetworkResponse(
                error = NetworkErrors.INTERNET,
                label = label
            )
        }
        is SocketException -> {
            val label = processNetworkErrorsForUi(NetworkErrors.INTERNET)
            NetworkResponse(
                error = NetworkErrors.INTERNET,
                label = label
            )
        }
        is SocketTimeoutException -> {
            val label = processNetworkErrorsForUi(NetworkErrors.REQUEST_TIMEOUT)
            NetworkResponse(
                error = NetworkErrors.REQUEST_TIMEOUT,
                label = label
            )
        }
        else -> {
            val label = processNetworkErrorsForUi(NetworkErrors.UNKNOWN)
            NetworkResponse(
                error = NetworkErrors.UNKNOWN,
                label = label
            )
        }
    }
}