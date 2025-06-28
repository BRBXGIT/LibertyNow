package com.example.design_system.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.design_system.R
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mTypography

@Composable
fun ErrorSection(
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.error_animation))
    Box(
        modifier = modifier.padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LottieAnimation(
                composition = composition,
                modifier = Modifier.size(170.dp),
                iterations = LottieConstants.IterateForever
            )

            Text(
                text = "Something went wrong :(",
                style = mTypography.bodyLarge.copy(
                    lineBreak = LineBreak.Paragraph
                ),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview
@Composable
fun ErrorSectionPreview() {
    LibriaNowTheme {
        ErrorSection()
    }
}