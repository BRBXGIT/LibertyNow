package com.example.navbar_screens.lists_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.dispatchers.Dispatcher
import com.example.common.dispatchers.LibriaNowDispatchers
import com.example.data.domain.ListsRepo
import com.example.local.db.lists_db.ListAnimeStatus
import com.example.local.db.lists_db.ListsAnimeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListsScreenVM @Inject constructor(
    private val repository: ListsRepo,
    @Dispatcher(LibriaNowDispatchers.IO) private val dispatcherIo: CoroutineDispatcher
) : ViewModel() {
    private val _listsScreenState = MutableStateFlow(ListsScreenState())
    val listsScreenState = _listsScreenState.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        ListsScreenState()
    )

    private fun updateScreenState(state: ListsScreenState) {
        _listsScreenState.value = state
    }

    private fun observeLists() {
        viewModelScope.launch(dispatcherIo) {
            val statusFlows: List<Pair<ListAnimeStatus, Flow<List<ListsAnimeEntity>>>> = ListAnimeStatus.entries.map { status ->
                status to repository.getAnimeByStatus(status)
            }

            val flows = statusFlows.map { it.second }

            combine(flows) { allAnimeLists ->
                val map = ListAnimeStatus.entries.toTypedArray().zip(allAnimeLists).toMap()
                ListsScreenState(animeByStatus = map)
            }.collect { newState ->
                _listsScreenState.value = newState
            }
        }
    }

    fun sendIntent(intent: ListsScreenIntent) {
        when(intent) {
            is ListsScreenIntent.UpdateScreenState -> updateScreenState(intent.state)
        }
    }

    init {
        observeLists()
    }
}
