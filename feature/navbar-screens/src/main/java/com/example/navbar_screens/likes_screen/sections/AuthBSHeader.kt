package com.example.navbar_screens.likes_screen.sections

import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.LibriaNowIcons
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mColors
import com.example.design_system.theme.mTypography

@Composable
fun AuthBSHeader(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Авторизация",
            style = mTypography.titleLarge
        )

        TextField(
            value = email,
            onValueChange = { onEmailChange(it) },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            placeholder = { Text("Логин или электронная почта") },
            leadingIcon = {
                Icon(
                    painter = painterResource(LibriaNowIcons.User),
                    contentDescription = null
                )
            }
        )

        var isVisible by rememberSaveable { mutableStateOf(false) }
        TextField(
            value = password,
            onValueChange = { onPasswordChange(it) },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            placeholder = { Text("Пароль") },
            visualTransformation = if(!isVisible) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = {
                Icon(
                    painter = painterResource(LibriaNowIcons.Password),
                    contentDescription = null
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = { isVisible = !isVisible }
                ) {
                    val animatedImage = AnimatedImageVector.animatedVectorResource(LibriaNowIcons.EyeAnimated)
                    val animatedPainter = rememberAnimatedVectorPainter(animatedImageVector = animatedImage, atEnd = isVisible)

                    Image(
                        painter = animatedPainter,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(mColors.onSurfaceVariant)
                    )
                }
            }
        )
    }
}

@Preview
@Composable
private fun AuthBSHeaderPreview() {
    LibriaNowTheme {
        AuthBSHeader(
            email = "",
            onEmailChange = {},
            password = "",
            onPasswordChange = {}
        )
    }
}