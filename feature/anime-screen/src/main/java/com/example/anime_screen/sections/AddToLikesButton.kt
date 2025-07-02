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
import androidx.compose.ui.graphics.Color
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
    val (animatedButtonColor, animatedInLikesAlpha, animatedNotInLikesAlpha) =
        rememberLikeButtonAnimations(alreadyInLikes, isLogged)

    val animatedAuthAlpha by animateFloatAsState(
        targetValue = if (!isLogged) 1f else 0f,
        animationSpec = tween(CommonConstants.ANIMATION_DURATION)
    )

    val onClick = when {
        isLogged && alreadyInLikes -> onPopClick
        isLogged -> onAddClick
        else -> onAuthClick
    }

    Button(
        colors = ButtonDefaults.buttonColors(containerColor = animatedButtonColor),
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = CommonConstants.HORIZONTAL_PADDING.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            LikeButtonContent(
                icon = LibriaNowIcons.MinusCircle,
                text = "Удалить из избранного",
                alpha = animatedInLikesAlpha
            )
            LikeButtonContent(
                icon = LibriaNowIcons.PlusCircle,
                text = "Добавить в избранное",
                alpha = animatedNotInLikesAlpha
            )
            LikeButtonContent(
                icon = LibriaNowIcons.UserCheck,
                text = "Авторизация",
                alpha = animatedAuthAlpha
            )
        }
    }
}

@Composable
private fun LikeButtonContent(
    icon: Int,
    text: String,
    alpha: Float
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.alpha(alpha)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Text(text)
    }
}

@Composable
private fun rememberLikeButtonAnimations(alreadyInLikes: Boolean, isLogged: Boolean): Triple<Color, Float, Float> {
    val animatedButtonColor by animateColorAsState(
        targetValue = if (alreadyInLikes && isLogged) mColors.secondary else mColors.primary,
        label = "Animated color for button",
        animationSpec = tween(CommonConstants.ANIMATION_DURATION)
    )
    val animatedInLikesAlpha by animateFloatAsState(
        targetValue = if (alreadyInLikes && isLogged) 1f else 0f,
        animationSpec = tween(CommonConstants.ANIMATION_DURATION)
    )
    val animatedNotInLikesAlpha by animateFloatAsState(
        targetValue = if (!alreadyInLikes && isLogged) 1f else 0f,
        animationSpec = tween(CommonConstants.ANIMATION_DURATION)
    )
    return Triple(animatedButtonColor, animatedInLikesAlpha, animatedNotInLikesAlpha)
}

@Preview
@Composable
private fun LikeButtonContentPreview() {
    LibriaNowTheme {
        LikeButtonContent(
            icon = LibriaNowIcons.UserCheck,
            text = "Авторизация",
            alpha = 1f
        )
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