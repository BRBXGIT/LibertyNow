package com.example.anime_screen.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.design_system.theme.CommonConstants
import com.example.design_system.theme.LibriaNowIcons
import com.example.design_system.theme.LibriaNowTheme
import com.example.design_system.theme.mTypography
import com.example.network.anime_screen.models.anime_details_response.Torrent

@Composable
fun TorrentsSection(
    modifier: Modifier = Modifier,
    torrents: List<Torrent>,
    onDownloadClick: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        torrents.forEach { torrent ->
            TorrentItem(
                label = torrent.label,
                size = (torrent.size / 8 / 1024 / 1024).toInt(),
                leechers = torrent.leechers,
                seeders = torrent.seeders,
                onDownloadCLick = { onDownloadClick(torrent.magnet) }
            )
        }
    }
}

@Composable
private fun TorrentItem(
    size: Int,
    label: String,
    leechers: Int,
    seeders: Int,
    onDownloadCLick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = CommonConstants.HORIZONTAL_PADDING.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = label,
                style = mTypography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$size mb",
                    style = mTypography.bodyLarge
                )

                Icon(
                    painter = painterResource(id = LibriaNowIcons.DoubleArrowUpFilled),
                    contentDescription = null,
                    tint = Color(0xff05da73)
                )

                Text(
                    text = "$seeders ",
                    style = mTypography.bodyLarge
                )

                Icon(
                    painter = painterResource(id = LibriaNowIcons.DoubleArrowDownFilled),
                    contentDescription = null,
                    tint = Color(0xffff2729)
                )

                Text(
                    text = leechers.toString(),
                    style = mTypography.bodyLarge
                )
            }
        }

        IconButton(
            onClick = onDownloadCLick
        ) {
            Icon(
                painter = painterResource(id = LibriaNowIcons.DownloadFilled),
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun TorrentItemPreview() {
    LibriaNowTheme {
        TorrentItem(
            label = "Sword Art Online- Alicization - AniLibria.TOP [HDTVRip 1080p][AVC][1-24]",
            size = 488,
            leechers = 24,
            seeders = 13,
            onDownloadCLick = {}
        )
    }
}