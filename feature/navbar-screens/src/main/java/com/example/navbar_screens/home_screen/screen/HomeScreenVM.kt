package com.example.navbar_screens.home_screen.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.data.domain.HomeScreenRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenVM @Inject constructor(
    repository: HomeScreenRepo
): ViewModel() {

    val titlesUpdate = repository.getTitlesUpdates().cachedIn(viewModelScope)
}