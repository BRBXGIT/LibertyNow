package com.example.common.common

sealed interface CommonIntent {
    data class ChangeNavIndex(val index: Int): CommonIntent
}