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
    QuickRewindBox(
        modifier = Modifier
            .align(Alignment.CenterStart)
            .fillMaxWidth(0.2f),
        label = "-5 секунд",
        onDoubleClick = onLeftClick,
        onSingleClick = onSingleClick
    )

    QuickRewindBox(
        modifier = Modifier
            .align(Alignment.CenterEnd)
            .fillMaxWidth(0.2f),
        label = "+5 секунд",
        onDoubleClick = onRightClick,
        onSingleClick = onSingleClick
    )
}

@Composable
private fun QuickRewindBox(
    modifier: Modifier,
    label: String,
    onDoubleClick: () -> Unit,
    onSingleClick: () -> Unit
) {
    var toggled by rememberSaveable { mutableStateOf(false) }

    val labelAlpha by animateFloatAsState(
        targetValue = if (toggled) 1f else 0f
    )

    LaunchedEffect(toggled) {
        if (toggled) {
            delay(300)
            toggled = false
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .zIndex(1f)
            .fillMaxHeight()
            .combinedClickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onSingleClick,
                onDoubleClick = {
                    onDoubleClick()
                    toggled = true
                }
            )
    ) {
        Text(
            text = label,
            style = mTypography.bodyLarge,
            modifier = Modifier.alpha(labelAlpha),
            color = Color.White
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