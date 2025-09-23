package com.example.common.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.network.common.utils.NetworkException

// TODO Create test
@Composable
fun <T : Any> PagingErrorContainer(
    items: LazyPagingItems<T>,
    onRetryRequest: (label: String, retry: () -> Unit) -> Unit,
) {
    val refreshState = items.loadState.refresh
    LaunchedEffect(refreshState) {
        val err = (refreshState as? LoadState.Error)?.error ?: return@LaunchedEffect
        val net = err as NetworkException
        onRetryRequest(net.label) { items.retry() }
    }
}