package com.example.navbar_screens.common

import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.foundation.Image
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mColors

@Composable
fun BottomNavBar(
    onNavItemClick: (Int, Any) -> Unit,
    selectedItemIndex: Int
) {
    NavigationBar {
        navItems.forEachIndexed { index, navItem ->
            val isSelected = index == selectedItemIndex
            var animatedSelection by rememberSaveable { mutableStateOf(false) }

            // Change local state to start animation
            LaunchedEffect(isSelected) {
                animatedSelection = isSelected
            }

            val animatedImage = AnimatedImageVector.animatedVectorResource(navItem.icon)
            val animatedPainter = rememberAnimatedVectorPainter(animatedImageVector = animatedImage, atEnd = animatedSelection)

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        onNavItemClick(index, navItem.destination)
                    }
                },
                icon = {
                    Image(
                        painter = animatedPainter,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(mColors.onSecondaryContainer),
                    )
                },
                label = {
                    Text(
                        text = navItem.label,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    }
}

@Preview
@Composable
private fun BottomNavBarPreview() {
    LibriaNowTheme {
        BottomNavBar(
            selectedItemIndex = 3,
            onNavItemClick = { index, route ->

            },
        )
    }
}