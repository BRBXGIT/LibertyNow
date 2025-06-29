package com.example.navbar_screens.likes_screen.sections

import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.CommonConstants
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mShapes

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationGraphicsApi::class)
@Composable
fun AuthBS(
    email: String,
    password: String,
    onDismissRequest: () -> Unit,
    onAuthClick: () -> Unit,
    onPasswordChange: (String) -> Unit,
    onEmailChange: (String) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        shape = mShapes.small,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(64.dp),
            modifier = Modifier.padding(CommonConstants.HORIZONTAL_PADDING.dp)
        ) {
            AuthBSHeader(
                email = email,
                onEmailChange = { onEmailChange(it) },
                password = password,
                onPasswordChange = { onPasswordChange(it) }
            )

            AuthBSFooter(
                onAuthClick = onAuthClick
            )
        }
    }
}

@Preview
@Composable
private fun AuthBSPreview() {
    LibriaNowTheme {
        AuthBS(
            email = "",
            password = "",
            onDismissRequest = {},
            onAuthClick = {},
            onPasswordChange = {},
            onEmailChange = {}
        )
    }
}