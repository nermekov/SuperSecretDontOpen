package com.example.supersecret

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.supersecret.click_checker.ClickSpamCheckerState
import com.example.supersecret.click_checker.ClickSpamCheckerStateImpl
import com.example.supersecret.list_state.FlowSummatorListState
import com.example.supersecret.list_state.FlowSummatorListStateImpl
import com.example.supersecret.text_state.FlowSummatorNumberState
import com.example.supersecret.text_state.FlowSummatorNumberStateImpl

internal class FlowSummatorVM : ViewModel(), FlowSummatorUIActions {

    private val flowListState: FlowSummatorListState = FlowSummatorListStateImpl(viewModelScope)

    private val flowNumberState: FlowSummatorNumberState =
        FlowSummatorNumberStateImpl(viewModelScope)

    private val clickSpamCheckerState: ClickSpamCheckerState = ClickSpamCheckerStateImpl()

    val itemsState = flowListState.itemsState

    val textState = flowNumberState.textState


    override fun onSubmitClicked() {
        if (flowNumberState.getN() < 1) return
        if (clickSpamCheckerState.checkClickForbidden()) return
        flowListState.startSummator(flowNumberState.getN())
    }
}