package com.example.anime_screen.sections

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.CommonConstants

@Composable
fun AddToLikesButton(
    alreadyInLikes: Boolean,
    onAddClick: () -> Unit
) {
    Button(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = CommonConstants.HORIZONTAL_PADDING.dp),
    ) {
        AnimatedVisibility(
            visible = alreadyInLikes,
            enter = slideInHorizontally(tween(300)) + fadeIn(tween(300)),
            exit = slideOutHorizontally(tween(300)) + fadeOut(tween(300))
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {

            }
        }
    }
}