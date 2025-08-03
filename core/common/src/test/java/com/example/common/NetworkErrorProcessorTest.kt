package com.example.common

import androidx.paging.PagingSource.LoadResult
import com.example.common.functions.NetworkErrors
import com.example.common.functions.NetworkException
import com.example.common.functions.NetworkResponse
import com.example.common.functions.processNetworkErrors
import com.example.common.functions.processNetworkErrorsForUi
import com.example.common.functions.processNetworkExceptions
import com.example.common.functions.processNetworkExceptionsForPaging
import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkErrorProcessorTest {

    @Test
    fun `returns correct NetworkErrors for http codes`() {
        assertEquals(NetworkErrors.SUCCESS, processNetworkErrors(200))
        assertEquals(NetworkErrors.UNAUTHORIZED, processNetworkErrors(401))
        assertEquals(NetworkErrors.REQUEST_TIMEOUT, processNetworkErrors(408))
        assertEquals(NetworkErrors.CONFLICT, processNetworkErrors(409))
        assertEquals(NetworkErrors.PAYLOAD_TOO_LARGE, processNetworkErrors(413))
        assertEquals(NetworkErrors.TOO_MANY_REQUESTS, processNetworkErrors(429))
        assertEquals(NetworkErrors.SERVER_ERROR, processNetworkErrors(503))
        assertEquals(NetworkErrors.UNKNOWN, processNetworkErrors(600))
    }

    @Test
    fun `returns correct labels for ui`() {
        assertEquals("Всё отлично", processNetworkErrorsForUi(NetworkErrors.SUCCESS))
        assertEquals("Кажется вы не авторизованы", processNetworkErrorsForUi(NetworkErrors.UNAUTHORIZED))
        assertEquals("Таймаут апи, попробуйте перезагрузить", processNetworkErrorsForUi(NetworkErrors.REQUEST_TIMEOUT))
        assertEquals("Кажется что-то конфликтует", processNetworkErrorsForUi(NetworkErrors.CONFLICT))
        assertEquals("Нагрузка на сервер слишком большая", processNetworkErrorsForUi(NetworkErrors.PAYLOAD_TOO_LARGE))
        assertEquals("Слишком много запросов, дайте AniLiberty немного отдохнуть", processNetworkErrorsForUi(NetworkErrors.TOO_MANY_REQUESTS))
        assertEquals("Ошибка сервера", processNetworkErrorsForUi(NetworkErrors.SERVER_ERROR))
        assertEquals("Проблема с сериализацией", processNetworkErrorsForUi(NetworkErrors.SERIALIZATION))
        assertEquals("Неизвестная ошибка, попробуйте с впн", processNetworkErrorsForUi(NetworkErrors.UNKNOWN))
    }

    @Test
    fun `returns the correct network response`() {
        val internetError = NetworkErrors.INTERNET
        val internetLabel = processNetworkErrorsForUi(internetError)
        assertEquals(
            NetworkResponse(
                error = internetError,
                label = internetLabel
            ),
            processNetworkExceptions(UnknownHostException())
        )
        assertEquals(
            NetworkResponse(
                error = internetError,
                label = internetLabel
            ),
            processNetworkExceptions(SocketException())
        )

        val timeoutError = NetworkErrors.REQUEST_TIMEOUT
        val timeoutLabel = processNetworkErrorsForUi(timeoutError)
        assertEquals(
            NetworkResponse(
                error = timeoutError,
                label = timeoutLabel
            ),
            processNetworkExceptions(SocketTimeoutException())
        )

        val unknownError = NetworkErrors.UNKNOWN
        val unknownLabel = processNetworkErrorsForUi(unknownError)
        assertEquals(
            NetworkResponse(
                error = unknownError,
                label = unknownLabel
            ),
            processNetworkExceptions(Exception())
        )
    }

    @Test
    fun `returns the correct network exceptions for paging`() {
        val unknownHostExceptionResult = processNetworkExceptionsForPaging<String, String>(UnknownHostException())
        val unknownHostExceptionError = (unknownHostExceptionResult as LoadResult.Error).throwable as NetworkException
        assertEquals(NetworkErrors.INTERNET, unknownHostExceptionError.error)

        val socketExceptionResult = processNetworkExceptionsForPaging<String, String>(SocketException())
        val socketExceptionError = (socketExceptionResult as LoadResult.Error).throwable as NetworkException
        assertEquals(NetworkErrors.INTERNET, socketExceptionError.error)

        val socketTimeoutExceptionResult = processNetworkExceptionsForPaging<String, String>(SocketTimeoutException())
        val socketTimeoutExceptionError = (socketTimeoutExceptionResult as LoadResult.Error).throwable as NetworkException
        assertEquals(NetworkErrors.REQUEST_TIMEOUT, socketTimeoutExceptionError.error)

        val unknownExceptionResult = processNetworkExceptionsForPaging<String, String>(Exception())
        val unknownExceptionError = (unknownExceptionResult as LoadResult.Error).throwable as NetworkException
        assertEquals(NetworkErrors.UNKNOWN, unknownExceptionError.error)
    }
}