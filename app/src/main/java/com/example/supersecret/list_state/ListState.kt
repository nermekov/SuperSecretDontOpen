package com.example.supersecret.list_state

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.supersecret.UiRow

internal interface ListState {
    /**
     * Немного засекретил код, чтобы не находили с гугла
     */
    fun startSecretOperation(n: Int)

    val itemsState: SnapshotStateList<UiRow>
}