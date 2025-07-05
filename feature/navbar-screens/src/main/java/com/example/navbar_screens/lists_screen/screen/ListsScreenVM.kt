package com.example.navbar_screens.lists_screen.screen

import androidx.lifecycle.ViewModel
import com.example.data.domain.ListsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListsScreenVM @Inject constructor(
    private val repository: ListsRepo
): ViewModel() {


}