package com.example.navbar_screens.lists_screen.sections

import android.annotation.SuppressLint
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mColors
import com.example.local.db.lists_db.ListAnimeStatus
import com.example.local.db.lists_db.ListsAnimeEntity

@Composable
fun ListsTabRow(
    selectedTabIndex: Int,
    animeByStatus: Map<ListAnimeStatus, List<ListsAnimeEntity>>,
    onTabClick: (Int, ListAnimeStatus) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        edgePadding = 16.dp,
        divider = { HorizontalDivider() },
        indicator = { tabPositions ->
            if (selectedTabIndex < tabPositions.size) {
                TabRowIndicator(
                    tabPositions = tabPositions,
                    selectedTabIndex = selectedTabIndex
                )
            }
        },
    ) {
        animeByStatus.keys.forEachIndexed { index, status ->
            val name = when(status) {
                ListAnimeStatus.WATCHING -> "Смотрю"
                ListAnimeStatus.COMPLETED -> "Просмотрено"
                ListAnimeStatus.PLANNED -> "В планах"
                ListAnimeStatus.ON_HOLD -> "Отложено"
                ListAnimeStatus.DROPPED -> "Брошено"
                ListAnimeStatus.HISTORY -> "История"
            }
            Tab(
                selected = index == selectedTabIndex,
                onClick = { onTabClick(index, status) },
                text = { Text(name) },
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 8.dp,
                            topEnd = 8.dp
                        )
                    )
            )
        }
    }
}

@Composable
private fun TabRowIndicator(
    tabPositions: List<TabPosition>,
    selectedTabIndex: Int
) {
    Box(
        modifier = Modifier
            .customTabIndicatorOffset(
                currentTabPosition = tabPositions[selectedTabIndex],
                width = 50.dp
            )
            .height(3.dp)
            .background(
                color = mColors.primary,
                shape = RoundedCornerShape(3.dp)
            )
    )
}

@SuppressLint("UseOfNonLambdaOffsetOverload")
private fun Modifier.customTabIndicatorOffset(
    currentTabPosition: TabPosition,
    width: Dp
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "tabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val currentTabWidth by animateDpAsState(
        targetValue = currentTabPosition.width,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing), label = ""
    )
    val indicatorOffset by animateDpAsState(
        targetValue = currentTabPosition.left,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing), label = ""
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .width(width)
        .offset(x = max(0.dp, currentTabWidth - width) / 2 + indicatorOffset)
}

@Preview
@Composable
fun ListsTabRowPreview() {
    LibriaNowTheme {
        ListsTabRow(
            selectedTabIndex = 1,
            animeByStatus = emptyMap(),
            onTabClick = { index, status -> }
        )
    }
}