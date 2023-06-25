package com.example.windytestovoe

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

private const val CLICK_SPAM_INTERVAL_MS = 1000
private const val BASIC_FLOW_INTERVAL_MS = 100L

internal class FlowSummatorVM : ViewModel(), UIActions {

    val itemsState = mutableStateListOf<Pair<Int, MutableList<Int>>>()

    private var lastClickedMillis = 0L

    private var flowsJob: Job? = null


    override fun onSubmit(n: Int) {
        if (System.currentTimeMillis() - lastClickedMillis < CLICK_SPAM_INTERVAL_MS)
            return
        lastClickedMillis = System.currentTimeMillis()
        flowsJob?.cancel()
        itemsState.add(Pair(n, mutableStateListOf()))
        val flowArray = Array(n) { index ->
            flow {
                while (true) {
                    delay((index + 1L) * BASIC_FLOW_INTERVAL_MS)
                    emit(index + 1)
                }
            }
        }
        flowsJob = viewModelScope.launch {
            var flow = flowArray[0]
            var totalSum = 0
            for (i in 1 until flowArray.size)
                flow = flow.zip(flowArray[i]) { p1, p2 -> p1 + p2 }
            flow.sample(100)
                .collectLatest { sum ->
                    totalSum += sum
                    itemsState.lastOrNull()?.let {
                        it.second.add(totalSum)
                    }
                }
        }
    }
}