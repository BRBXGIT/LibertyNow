package com.example.design_system.sections.auth_bs

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mColors
import com.example.design_system.theme.mShapes
import com.example.design_system.theme.mTypography

@Composable
fun AuthBSFooter(
    onAuthClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onAuthClick,
            shape = mShapes.small
        ) {
            Text(
                text = "Авторизоваться"
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .background(
                    color = mColors.surfaceContainerHighest,
                    shape = mShapes.small
                )
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Row {
                Text(
                    text = "Новый пользователь? ",
                    style = mTypography.labelLarge
                )

                val context = LocalContext.current
                Text(
                    text = "Регистрация",
                    style = mTypography.labelLarge.copy(
                        color = mColors.primary,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            context.startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    "https://anilibria.top/app/auth/registration/newRegistration".toUri()
                                )
                            )
                        }
                )
            }
        }
    }
}

@Preview
@Composable
private fun AuthBSFooterPreview() {
    LibriaNowTheme {
        AuthBSFooter(
            onAuthClick = {}
        )
    }
}