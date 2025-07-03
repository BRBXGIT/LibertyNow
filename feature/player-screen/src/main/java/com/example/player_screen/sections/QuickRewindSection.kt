package com.example.player_screen.sections

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.zIndex
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mTypography
import kotlinx.coroutines.delay

@Composable
fun BoxScope.QuickRewindSection(
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
    onSingleClick: () -> Unit
) {
    var leftBoxToggled by rememberSaveable { mutableStateOf(false) }
    val leftLabelAlpha by animateFloatAsState(
        targetValue = if (leftBoxToggled) 1f else 0f
    )
    LaunchedEffect(leftBoxToggled) {
        if (leftBoxToggled) {
            delay(300)
            leftBoxToggled = false
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .align(Alignment.CenterStart)
            .zIndex(1f)
            .fillMaxHeight()
            .fillMaxWidth(0.2f)
            .combinedClickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onSingleClick,
                onDoubleClick = {
                    onLeftClick()
                    leftBoxToggled = true
                }
            )
    ) {
        Text(
            text = "-5 секунд",
            style = mTypography.bodyLarge,
            modifier = Modifier.alpha(leftLabelAlpha),
            color = Color(0xFFFFFFFF)
        )
    }

    var rightBoxToggled by rememberSaveable { mutableStateOf(false) }
    val rightLabelAlpha by animateFloatAsState(
        targetValue = if (rightBoxToggled) 1f else 0f
    )
    LaunchedEffect(rightBoxToggled) {
        if (rightBoxToggled) {
            delay(300)
            rightBoxToggled = false
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .align(Alignment.CenterEnd)
            .zIndex(1f)
            .fillMaxHeight()
            .fillMaxWidth(0.2f)
            .combinedClickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onSingleClick,
                onDoubleClick = {
                    onRightClick()
                    rightBoxToggled = true
                }
            )
    ) {
        Text(
            text = "+5 секунд",
            style = mTypography.bodyLarge,
            modifier = Modifier.alpha(rightLabelAlpha),
            color = Color(0xFFFFFFFF)
        )
    }
}

@Preview
@Composable
fun QuickRewindSectionPreview() {
    LibriaNowTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            QuickRewindSection(
                onLeftClick = {},
                onRightClick = {},
                onSingleClick = {}
            )
        }
    }
}