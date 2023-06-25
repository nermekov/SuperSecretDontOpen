package com.example.supersecret

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

private const val CLICK_SPAM_INTERVAL_MS = 1000
private const val BASIC_FLOW_INTERVAL_MS = 100L
private const val INPUT_NUMBER_LIMIT = 1000

internal class FlowSummatorVM : ViewModel(), FlowSummatroUIActions {

    val itemsState = mutableStateListOf<UiRow>()
    val textState = mutableStateOf("")

    private var lastClickedMillis = 0L

    private var flowsJob: Job? = null

    private var n = 0

    init {
        snapshotFlow { textState.value }
            .drop(1)
            .onEach {
                onTextChanged(it)
            }.launchIn(viewModelScope)
    }

    override fun onSubmitClicked() {
        if (n < 1) return
        if (checkClickForbidden()) return
        flowsJob?.cancel()
        itemsState.add(UiRow(n, mutableStateListOf()))
        launchFlowsCollection(createFlowArray(n))
    }

    private fun onTextChanged(text: String) {
        try {
            when {
                text == "0" -> {
                    n = 0
                    textState.value = ""
                }
                text.isBlank() -> n = 0
                text.toInt() < INPUT_NUMBER_LIMIT -> n = text.toInt()
                else -> textState.value = n.toString()
            }
        } catch (_: Exception) {
            textState.value = ""
            n = 0
        }
    }

    private fun launchFlowsCollection(flowArray: Array<Flow<Int>>) {
        flowsJob = viewModelScope.launch {
            var totalSum = 0
            merge(*flowArray).collectLatest {
                totalSum += it
                itemsState.lastOrNull()?.let { pair ->
                    pair.calculatedValues.add(totalSum)
                }
            }
        }
    }

    private fun createFlowArray(n: Int) = Array(n) { index ->
        flow {
            delay((index + 1L) * BASIC_FLOW_INTERVAL_MS)
            emit(index + 1)
        }
    }
    
    private fun checkClickForbidden(): Boolean {
        val isClickForbidden = System.currentTimeMillis() - lastClickedMillis < CLICK_SPAM_INTERVAL_MS
        if (!isClickForbidden)
            lastClickedMillis = System.currentTimeMillis()
        return isClickForbidden
    }
}