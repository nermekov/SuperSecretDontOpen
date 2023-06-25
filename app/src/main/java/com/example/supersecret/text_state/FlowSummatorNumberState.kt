package com.example.supersecret.text_state

import androidx.compose.runtime.MutableState

internal interface FlowSummatorNumberState {
    val textState: MutableState<String>

    fun getN(): Int
}