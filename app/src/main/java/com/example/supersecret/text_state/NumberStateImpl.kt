package com.example.supersecret.text_state

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val INPUT_NUMBER_LIMIT = 1000

internal class NumberStateImpl(
    vmScope: CoroutineScope
) : NumberState {
    override val textState = mutableStateOf("")

    private var mN = 0

    init {
        snapshotFlow { textState.value }
            .drop(1)
            .onEach {
                onTextChanged(it)
            }.launchIn(vmScope)
    }

    override fun getN(): Int = mN

    private fun onTextChanged(text: String) {
        try {
            when {
                text == "0" -> {
                    mN = 0
                    textState.value = ""
                }
                text.isBlank() -> mN = 0
                text.toInt() < INPUT_NUMBER_LIMIT -> mN = text.toInt()
                else -> textState.value = mN.toString()
            }
        } catch (_: Exception) {
            textState.value = ""
            mN = 0
        }
    }
}