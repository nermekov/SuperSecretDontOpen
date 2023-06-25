package com.example.supersecret.click_checker

private const val CLICK_SPAM_INTERVAL_MS = 1000

internal class ClickSpamCheckerStateImpl: ClickSpamCheckerState {

    private var lastClickedMillis = 0L

    override fun checkClickForbidden(): Boolean {
        val isClickForbidden = System.currentTimeMillis() - lastClickedMillis < CLICK_SPAM_INTERVAL_MS
        if (!isClickForbidden)
            lastClickedMillis = System.currentTimeMillis()
        return isClickForbidden
    }
}