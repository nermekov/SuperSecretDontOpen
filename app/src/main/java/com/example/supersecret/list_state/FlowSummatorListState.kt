package com.example.supersecret.list_state

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.supersecret.UiRow

internal interface FlowSummatorListState {
    fun startSummator(n: Int)

    val itemsState: SnapshotStateList<UiRow>
}