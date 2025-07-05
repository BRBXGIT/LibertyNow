package com.example.simple_screens.project_team_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mColors
import com.example.design_system.theme.mTypography
import com.example.network.project_team_screen.models.ProjectTeamResponse

@Composable
fun ProjectTeamLC(
    projectTeam: ProjectTeamResponse,
    bottomPadding: Dp
) {
    if (projectTeam != ProjectTeamResponse()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            projectTeamSection("Тайминги:", projectTeam.timing)
            projectTeamSection("Субтитры:", projectTeam.decor)
            projectTeamSection("Озвучка:", projectTeam.voice)
            projectTeamSection("Правки:", projectTeam.editing)
            projectTeamSection("Перевод:", projectTeam.translator)

            item(span = { GridItemSpan(maxLineSpan) }) {
                Text(
                    text = "Ну и я, разработчик приложения:",
                    style = mTypography.titleLarge.copy(color = mColors.primary)
                )
            }

            item {
                Column {
                    MemberCard("BRBX")
                    Spacer(modifier = Modifier.padding(bottom = bottomPadding))
                }
            }
        }
    }
}

private fun LazyGridScope.projectTeamSection(
    title: String,
    members: List<String>
) {
    if (members.isNotEmpty()) {
        item(span = { GridItemSpan(maxLineSpan) }) {
            Text(
                text = title,
                style = mTypography.titleLarge.copy(color = mColors.primary)
            )
        }

        items(members) { name ->
            MemberCard(name)
        }
    }
}

@Composable
private fun MemberCard(name: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        tonalElevation = 2.dp,
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            text = name,
            style = mTypography.bodyLarge,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Preview
@Composable
fun ProjectTeamLCPreview() {
    LibriaNowTheme {
        ProjectTeamLC(
            projectTeam = ProjectTeamResponse(),
            bottomPadding = 16.dp
        )
    }
}

@Preview
@Composable
fun MemberCardPreview() {
    LibriaNowTheme {
        MemberCard("BRBX")
    }
}