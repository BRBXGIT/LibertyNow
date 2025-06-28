package com.example.common

sealed class CommonIntent {
    data class UpdateState(val state: CommonState): CommonIntent()
}