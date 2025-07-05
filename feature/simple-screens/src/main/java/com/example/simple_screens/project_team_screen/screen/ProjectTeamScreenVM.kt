package com.example.simple_screens.project_team_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.LibriaNowDispatchers
import com.example.common.functions.NetworkErrors
import com.example.data.domain.ProjectTeamScreenRepo
import com.example.design_system.snackbars.SnackbarAction
import com.example.design_system.snackbars.SnackbarController
import com.example.design_system.snackbars.SnackbarEvent
import com.example.network.project_team_screen.models.ProjectTeamResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProjectTeamScreenVM @Inject constructor(
    private val repository: ProjectTeamScreenRepo,
    @Dispatcher(LibriaNowDispatchers.IO) private val dispatcherIo: CoroutineDispatcher
): ViewModel() {
    private val _projectTeamScreenState = MutableStateFlow(ProjectTeamScreenState())
    val projectTeamScreenState = _projectTeamScreenState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        ProjectTeamScreenState()
    )

    private fun fetchProjectTeam() {
        viewModelScope.launch(dispatcherIo) {
            _projectTeamScreenState.update { state ->
                state.copy(
                    isLoading = true,
                    isError = false
                )
            }

            val response = repository.getProjectTeam()
            if (response.error == NetworkErrors.SUCCESS) {
                _projectTeamScreenState.update { state ->
                    state.copy(
                        projectTeam = response.response as ProjectTeamResponse,
                        isError = false,
                        isLoading = false
                    )
                }
            } else {
                _projectTeamScreenState.update { state ->
                    state.copy(
                        isError = true,
                        isLoading = false
                    )
                }
                SnackbarController.sendEvent(
                    SnackbarEvent(
                        message = response.label!!,
                        action = SnackbarAction(
                            name = "Retry",
                            action = { fetchProjectTeam() }
                        )
                    )
                )
            }
        }
    }

    init {
        fetchProjectTeam()
    }
}