package com.example.anime_screen.sections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
    onPopClick: () -> Unit
) {
    val animatedButtonColor by animateColorAsState(
        targetValue = if (alreadyInLikes) mColors.secondary else mColors.primary,
        label = "Animated color for button"
    )

    Button(
        colors = ButtonDefaults.buttonColors(containerColor = animatedButtonColor),
        onClick = if (alreadyInLikes) onPopClick else onAddClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = CommonConstants.HORIZONTAL_PADDING.dp)
    ) {
        AnimatedVisibility(
            visible = alreadyInLikes,
            enter = slideInHorizontally(tween(300)) + fadeIn(tween(300)),
            exit = slideOutHorizontally(tween(300)) + fadeOut(tween(300))
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(LibriaNowIcons.MinusCircle),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )

                Text(
                    text = "Удалить из избранного"
                )
            }
        }

        AnimatedVisibility(
            visible = !alreadyInLikes,
            enter = slideInHorizontally(tween(300)) + fadeIn(tween(300)),
            exit = slideOutHorizontally(tween(300)) + fadeOut(tween(300))
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(LibriaNowIcons.PlusCircle),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )

                Text(
                    text = "Добавить в избранное"
                )
            }
        }
    }
}

@Preview
@Composable
fun AddToLikesButtonPreview() {
    LibriaNowTheme {
        AddToLikesButton(
            alreadyInLikes = true,
            onAddClick = {},
            onPopClick = {}
        )
    }
}