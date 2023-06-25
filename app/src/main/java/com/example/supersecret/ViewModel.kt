package com.example.supersecret

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supersecret.click_checker.ClickSpamCheckerState
import com.example.supersecret.click_checker.ClickSpamCheckerStateImpl
import com.example.supersecret.list_state.ListState
import com.example.supersecret.list_state.ListStateImpl
import com.example.supersecret.text_state.NumberState
import com.example.supersecret.text_state.NumberStateImpl

internal class ViewModel : ViewModel(), UIActions {

    private val flowListState: ListState = ListStateImpl(viewModelScope)

    private val flowNumberState: NumberState = NumberStateImpl(viewModelScope)

    private val clickSpamCheckerState: ClickSpamCheckerState = ClickSpamCheckerStateImpl()

    val itemsState = flowListState.itemsState

    val textState = flowNumberState.textState


    override fun onSubmitClicked() {
        if (flowNumberState.getN() < 1) return
        if (clickSpamCheckerState.checkClickForbidden()) return
        flowListState.startSecretOperation(flowNumberState.getN())
    }
}