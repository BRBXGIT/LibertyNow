package com.example.common.common

sealed class CommonIntent {
    data class UpdateState(val state: CommonState): CommonIntent()
}