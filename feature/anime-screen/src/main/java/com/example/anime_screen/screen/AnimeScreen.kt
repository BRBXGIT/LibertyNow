package com.example.anime_screen.screen

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.anime_screen.sections.AddToLikesButton
import com.example.anime_screen.sections.AnimeScreenTopBar
import com.example.anime_screen.sections.ContinueWatchFABWrapper
import com.example.anime_screen.sections.DescriptionSection
import com.example.anime_screen.sections.EpisodeItem
import com.example.anime_screen.sections.GenresLR
import com.example.anime_screen.sections.Header
import com.example.anime_screen.sections.ListsBS
import com.example.anime_screen.sections.TorrentsSection
import com.example.common.auth.AuthIntent
import com.example.common.auth.AuthState
import com.example.common.auth.AuthVM
import com.example.design_system.sections.auth_bs.AuthBS
import com.example.design_system.sections.error_section.ErrorSection
import com.example.design_system.snackbars.SnackbarObserver
import com.example.design_system.theme.CommonConstants
import com.example.design_system.theme.DesignUtils
import com.example.local.datastore.auth.LoggingState
import com.example.player_screen.navigation.PlayerScreenRoute
import com.google.gson.Gson

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

    // Fetch anime and watched eps
    HandleAnimeFetching(animeId, authState, screenState, viewModel)

    // Snackbars stuff
    val snackbarHostState = remember { SnackbarHostState() }
    SnackbarObserver(snackbarHostState)

    val anime = screenState.anime
    val topBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            AnimeScreenTopBar(
                animeTitle = screenState.anime?.name?.main,
                isLoading = screenState.isLoading or authState.isLoading,
                scrollBehavior = topBarScrollBehavior,
                isError = screenState.isError,
                onArchiveClick = {
                    viewModel.sendIntent(
                        AnimeScreenIntent.UpdateScreenState(
                            screenState.copy(isListsBSOpened = true)
                        )
                    )
                },
                onNavClick = { navController.navigateUp() },
                animeInList = screenState.currentListsAnimeIn.isNotEmpty()
            )
        },
        floatingActionButton = {
            ContinueWatchFABWrapper(
                screenState = screenState,
                animeId = animeId,
                onClick = { currentEpisodeId, linksString, host, animeId ->
                    navController.navigate(
                        PlayerScreenRoute(
                            currentEpisodeId = currentEpisodeId,
                            gsonLinks = linksString,
                            host = host,
                            animeId = animeId
                        )
                    )
                },
            )
        },
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(topBarScrollBehavior.nestedScrollConnection)
    ) { innerPadding ->
        if (screenState.isError) {
            ErrorSection(modifier = Modifier.fillMaxSize())
        } else {
            anime?.let {
                if (screenState.isListsBSOpened) {
                    ListsBS(
                        screenState = screenState,
                        viewModel = viewModel
                    )
                }

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

                val lazyListState = rememberLazyListState()
                val directionalLazyListState = rememberDirectionalLazyListState(
                    lazyListState
                )
                LaunchedEffect(directionalLazyListState.scrollDirection) {
                    viewModel.sendIntent(
                        AnimeScreenIntent.UpdateScreenState(
                            screenState.copy(scrollDirection = directionalLazyListState.scrollDirection)
                        )
                    )
                }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    state = lazyListState
                ) {
                    item(key = "header") {
                        Header(
                            nameEnglish = anime.name.english,
                            season = "${anime.season.description} ${anime.year}",
                            type = anime.type.description,
                            episodes = anime.episodes.size,
                            releaseState = if (anime.isOngoing) "Онгоинг" else "Завершено",
                            posterPath = DesignUtils.POSTERS_BASE_URL + anime.poster.preview,
                            topInnerPadding = innerPadding.calculateTopPadding() + 12.dp,
                            modifier = Modifier.animateItem()
                        )
                    }

                    // TODO
                    item(key = "addToLikeButton") {
                        AddToLikesButton(
                            modifier = Modifier.animateItem(),
                            alreadyInLikes = screenState.isInLikes,
                            isLoading = authState.isLoading,
                            onAddClick = {
//                                authVM.sendIntent(
//                                    AuthIntent.AddLike(currentAnime)
//                                )
//                                viewModel.sendIntent(
//                                    AnimeScreenIntent.UpdateScreenState(
//                                        screenState.copy(isInLikes = true)
//                                    )
//                                )
                            },
                            onPopClick = {
//                                authVM.sendIntent(
//                                    AuthIntent.RemoveLike(currentAnime)
//                                )
//                                viewModel.sendIntent(
//                                    AnimeScreenIntent.UpdateScreenState(
//                                        screenState.copy(isInLikes = false)
//                                    )
//                                )
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
                            genres = anime.genres.map { it.name },
                            modifier = Modifier.animateItem()
                        )
                    }

                    item(key = "description") {
                        DescriptionSection(
                            modifier = Modifier.animateItem(),
                            description = anime.description,
                            isExpanded = screenState.isDescriptionExpanded,
                            voiceActors = anime.members.filter { it.role.value == "voicing" }.joinToString(", ") { it.nickname },
                            timingWorkers = anime.members.filter { it.role.value == "timing" }.joinToString(", ") { it.nickname },
                            subtitlesWorkers = anime.members.filter { it.role.value == "decorating" }.joinToString(", ") { it.nickname },
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
                        items = anime.episodes,
                        key = { index, episode -> index }
                    ) { index, episode ->
                        val isWatched = index in screenState.watchedEps

                        EpisodeItem(
                            modifier = Modifier.animateItem(),
                            episode = index,
                            name = episode.name ?: "Без названия",
                            isWatched = isWatched,
                            onClick = {
                                val links = anime.episodes.map { it.hls480 + it.hls720 + it.hls1080 }
                                val linksString = Gson().toJson(links)

                                navController.navigate(
                                    PlayerScreenRoute(
                                        currentEpisodeId = index,
                                        gsonLinks = linksString,
                                        host = "",
                                        animeId = animeId
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

@Composable
private fun HandleAnimeFetching(
    animeId: Int,
    authState: AuthState,
    screenState: AnimeScreenState,
    viewModel: AnimeScreenVM
) {
    LaunchedEffect(Unit) {
        viewModel.sendIntent(AnimeScreenIntent.FetchAnime(animeId))
        viewModel.sendIntent(AnimeScreenIntent.ObserveWatchedEps(animeId))

        if (authState.isLogged is LoggingState.LoggedIn) {
            val isInLikes = animeId in authState.likes.map { it.id }
            viewModel.sendIntent(
                AnimeScreenIntent.UpdateScreenState(
                    state = screenState.copy(isInLikes = isInLikes)
                )
            )
        }
    }
}