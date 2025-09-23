package com.example.common.utils

import com.example.design_system.snackbars.SnackbarAction
import com.example.design_system.snackbars.SnackbarController
import com.example.design_system.snackbars.SnackbarEvent


// TODO Create tests
suspend fun sendRetrySnackbar(
    label: String,
    action: () -> Unit
) {
    SnackbarController.sendEvent(
        SnackbarEvent(
            message = label,
            action = SnackbarAction(
                name = "Retry",
                action = action
            )
        )
    )
}

suspend fun sendSimpleSnackbar(label: String) = SnackbarController.sendEvent(SnackbarEvent(message = label))