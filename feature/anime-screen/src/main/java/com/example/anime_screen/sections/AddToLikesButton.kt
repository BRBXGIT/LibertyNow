package com.example.anime_screen.sections

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.CommonConstants
import com.example.design_system.theme.LibriaNowIcons
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mColors

@Composable
fun AddToLikesButton(
    alreadyInLikes: Boolean,
    onAddClick: () -> Unit,
    onPopClick: () -> Unit,
    onAuthClick: () -> Unit,
    isLogged: Boolean,
    modifier: Modifier = Modifier
) {
    val animatedButtonColor by animateColorAsState(
        targetValue = if (alreadyInLikes and isLogged) mColors.secondary else mColors.primary,
        label = "Animated color for button",
        animationSpec = tween(CommonConstants.ANIMATION_DURATION)
    )
    val animatedInLikesAlpha by animateFloatAsState(
        targetValue = if (alreadyInLikes and isLogged) 1f else 0f,
        animationSpec = tween(CommonConstants.ANIMATION_DURATION),
        label = "Animation for label if anime already in likes"
    )
    val animatedNotInLikesAlpha by animateFloatAsState(
        targetValue = if (!alreadyInLikes and isLogged) 1f else 0f,
        animationSpec = tween(CommonConstants.ANIMATION_DURATION),
        label = "Animation for label if anime not in likes"
    )
    val animatedAuthAlpha by animateFloatAsState(
        targetValue = if (!isLogged) 1f else 0f,
        animationSpec = tween(CommonConstants.ANIMATION_DURATION),
        label = "Animation for label if user not logged in"
    )

    Button(
        colors = ButtonDefaults.buttonColors(containerColor = animatedButtonColor),
        onClick = if (isLogged) {
            if (alreadyInLikes) onPopClick else onAddClick
        } else {
            onAuthClick
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = CommonConstants.HORIZONTAL_PADDING.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(LibriaNowIcons.MinusCircle),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .alpha(animatedInLikesAlpha)
                )

                Text(
                    text = "Удалить из избранного",
                    modifier = Modifier.alpha(animatedInLikesAlpha)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(LibriaNowIcons.PlusCircle),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .alpha(animatedNotInLikesAlpha)
                )

                Text(
                    text = "Добавить в избранное",
                    modifier = Modifier.alpha(animatedNotInLikesAlpha)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(LibriaNowIcons.UserCheck),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .alpha(animatedAuthAlpha)
                )

                Text(
                    text = "Авторизация",
                    modifier = Modifier.alpha(animatedAuthAlpha)
                )
            }
        }
    }
}

@Preview
@Composable
private fun AddToLikesButtonPreview() {
    LibriaNowTheme {
        AddToLikesButton(
            alreadyInLikes = true,
            onAddClick = {},
            onPopClick = {},
            isLogged = true,
            onAuthClick = {},
        )
    }
}