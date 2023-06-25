package com.example.supersecret.list_state

import androidx.compose.runtime.mutableStateListOf
import com.example.supersecret.UiRow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.launch

private const val BASIC_FLOW_INTERVAL_MS = 100L

internal class FlowSummatorListStateImpl(private val vmScope: CoroutineScope) :
    FlowSummatorListState {
    override val itemsState = mutableStateListOf<UiRow>()

    private var flowsJob: Job? = null

    override fun startSummator(n: Int) {
        flowsJob?.cancel()
        itemsState.add(UiRow(n, mutableStateListOf()))
        launchFlowsCollection(createFlowArray(n))
    }

    private fun launchFlowsCollection(flowArray: Array<Flow<Int>>) {
        flowsJob = vmScope.launch {
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
}