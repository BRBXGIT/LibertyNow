package com.example.anime_screen.screen

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.anime_screen.sections.AddToLikesButton
import com.example.anime_screen.sections.AnimeScreenTopBar
import com.example.anime_screen.sections.DescriptionSection
import com.example.anime_screen.sections.EpisodeItem
import com.example.anime_screen.sections.GenresLR
import com.example.anime_screen.sections.Header
import com.example.anime_screen.sections.TorrentsSection
import com.example.common.auth.AuthIntent
import com.example.common.auth.AuthVM
import com.example.design_system.sections.auth_bs.AuthBS
import com.example.design_system.sections.error_section.ErrorSection
import com.example.design_system.snackbars.ObserveAsEvents
import com.example.design_system.snackbars.SnackbarController
import com.example.design_system.theme.CommonConstants
import com.example.design_system.theme.DesignUtils
import com.example.local.datastore.auth.LoggingState
import com.example.network.common.titles_list_response.Item0
import com.example.network.common.titles_list_response.Medium
import com.example.network.common.titles_list_response.Names
import com.example.network.common.titles_list_response.Original
import com.example.network.common.titles_list_response.Posters
import com.example.network.common.titles_list_response.Small
import com.example.player_screen.navigation.PlayerScreenRoute
import com.google.gson.Gson
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeScreen(
    animeId: Int,
    viewModel: AnimeScreenVM,
    authVM: AuthVM,
    navController: NavController
) {
    val authState by authVM.authState.collectAsStateWithLifecycle()
    val screenState by viewModel.animeScreenState.collectAsStateWithLifecycle()

    // Fetch anime
    LaunchedEffect(animeId, authState.likes, authState.isLogged) {
        viewModel.sendIntent(
            AnimeScreenIntent.FetchAnime(animeId)
        )
        if (authState.isLogged is LoggingState.LoggedIn) {
            val currentLikesIds = authState.likes.map { it.id }
            if (animeId in currentLikesIds) {
                viewModel.sendIntent(
                    AnimeScreenIntent.UpdateScreenState(
                        state = screenState.copy(isInLikes = true)
                    )
                )
            } else {
                viewModel.sendIntent(
                    AnimeScreenIntent.UpdateScreenState(
                        state = screenState.copy(isInLikes = false)
                    )
                )
            }
        }
    }

    // Snackbars stuff
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    ObserveAsEvents(flow = SnackbarController.events, snackbarHostState) { event ->
        scope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()

            val result = snackbarHostState.showSnackbar(
                message = event.message,
                actionLabel = event.action?.name,
                duration = SnackbarDuration.Indefinite,
                withDismissAction = true
            )

            if(result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }

    val topBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            AnimeScreenTopBar(
                animeTitle = screenState.anime?.names?.ru,
                isLoading = screenState.isLoading or authState.isLoading,
                scrollBehavior = topBarScrollBehavior,
                isError = screenState.isError,
                onArchiveClick = {}, // TODO
                onNavClick = { navController.navigateUp() }
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(topBarScrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        if (authState.isAuthBSOpened) {
            AuthBS(
                email = authState.email,
                password = authState.password,
                isPasswordVisible = authState.isPasswordVisible,
                incorrectEmail = authState.incorrectEmail,
                incorrectPassword = authState.incorrectPassword,
                onVisibleClick = {
                    authVM.sendIntent(
                        AuthIntent.UpdateAuthState(
                            authState.copy(isPasswordVisible = !authState.isPasswordVisible)
                        )
                    )
                },
                onDismissRequest = {
                    authVM.sendIntent(
                        AuthIntent.UpdateAuthState(
                            authState.copy(isAuthBSOpened = false)
                        )
                    )
                },
                onAuthClick = {
                    authVM.sendIntent(
                        AuthIntent.GetSessionToken
                    )
                },
                onPasswordChange = {
                    authVM.sendIntent(
                        AuthIntent.UpdateAuthState(
                            authState.copy(password = it)
                        )
                    )
                },
                onEmailChange = {
                    authVM.sendIntent(
                        AuthIntent.UpdateAuthState(
                            authState.copy(email = it)
                        )
                    )
                }
            )
        }

        val anime = screenState.anime
        if (screenState.isError) {
            ErrorSection(modifier = Modifier.fillMaxSize())
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                anime?.let {
                    item(key = "header") {
                        Header(
                            nameEnglish = anime.names.en,
                            season = "${anime.season.string} ${anime.season.year}",
                            type = anime.type.string,
                            episodes = anime.player.list.values.size,
                            releaseState = anime.status.string,
                            posterPath = DesignUtils.POSTERS_BASE_URL + anime.posters.original.url,
                            topInnerPadding = innerPadding.calculateTopPadding() + 12.dp,
                            modifier = Modifier.animateItem()
                        )
                    }

                    item(key = "addToLikeButton") {
                        val names = Names(
                            alternative = anime.names.alternative,
                            en = anime.names.en,
                            ru = anime.names.ru
                        )
                        val posters = Posters(
                            medium = Medium(
                                rawBase64File = anime.posters.medium.rawBase64File,
                                url = anime.posters.medium.url
                            ),
                            original = Original(
                                rawBase64File = anime.posters.original.rawBase64File,
                                url = anime.posters.original.url
                            ),
                            small = Small(
                                rawBase64File = anime.posters.small.rawBase64File,
                                url = anime.posters.small.url
                            )
                        )
                        val currentAnime = Item0(
                            id = animeId,
                            genres = anime.genres,
                            names = names,
                            posters = posters
                        )

                        AddToLikesButton(
                            modifier = Modifier.animateItem(),
                            alreadyInLikes = screenState.isInLikes,
                            onAddClick = {
                                authVM.sendIntent(
                                    AuthIntent.AddLike(currentAnime)
                                )
                            },
                            onPopClick = {
                                authVM.sendIntent(
                                    AuthIntent.RemoveLike(currentAnime)
                                )
                            },
                            isLogged = when (authState.isLogged) {
                                LoggingState.LoggedIn -> true
                                else -> false
                            },
                            onAuthClick = {
                                authVM.sendIntent(
                                    AuthIntent.UpdateAuthState(
                                        authState.copy(isAuthBSOpened = true)
                                    )
                                )
                            },
                        )
                    }

                    item(key = "genresLR") {
                        GenresLR(
                            genres = anime.genres,
                            modifier = Modifier.animateItem()
                        )
                    }

                    item(key = "description") {
                        DescriptionSection(
                            modifier = Modifier.animateItem(),
                            description = anime.description,
                            isExpanded = screenState.isDescriptionExpanded,
                            voiceActors = anime.team.voice.joinToString(", "),
                            timingWorkers = anime.team.timing.joinToString(", "),
                            subtitlesWorkers = anime.team.decor.joinToString(", "),
                            onExpandClick = {
                                viewModel.sendIntent(
                                    AnimeScreenIntent.UpdateScreenState(
                                        screenState.copy(isDescriptionExpanded = !screenState.isDescriptionExpanded)
                                    )
                                )
                            }
                        )
                    }

                    item(key = "torrents") {
                        val context = LocalContext.current

                        TorrentsSection(
                            modifier = Modifier.animateItem(),
                            torrents = anime.torrents,
                            onDownloadClick = { torrentLink ->
                                context.startActivity(
                                    Intent(
                                        Intent.ACTION_VIEW,
                                        (CommonConstants.BASE_TORRENTS_URL + torrentLink).toUri()
                                    )
                                )
                            }
                        )
                    }

                    item(key = "divider") {
                        HorizontalDivider(
                            modifier = Modifier
                                .animateItem()
                                .padding(horizontal = CommonConstants.HORIZONTAL_PADDING.dp)
                        )
                    }

                    itemsIndexed(
                        items = anime.player.list.values.toList(),
                        key = { index, episode -> index }
                    ) { index, episode ->
                        EpisodeItem(
                            modifier = Modifier.animateItem(),
                            episode = episode.episode,
                            name = episode.name ?: "Без названия",
                            onWatchButtonClick = {
                                val links = anime.player.list.values.toList()
                                val linksString = Gson().toJson(links)

                                navController.navigate(
                                    PlayerScreenRoute(
                                        currentItemId = index,
                                        gsonLinks = linksString,
                                        host = anime.player.host
                                    )
                                )
                            }
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(innerPadding.calculateBottomPadding()))
                    }
                }
            }
        }
    }
}