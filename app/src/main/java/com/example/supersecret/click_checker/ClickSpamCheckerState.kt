package com.example.supersecret.click_checker

internal interface ClickSpamCheckerState {
    fun checkClickForbidden(): Boolean
}