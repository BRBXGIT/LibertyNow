package com.example.common.common

sealed interface CommonIntent {
    data class UpdateState(val state: CommonState): CommonIntent
}