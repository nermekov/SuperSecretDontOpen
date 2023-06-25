package com.example.supersecret

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.supersecret.ui.flowsum.FlowSummator
import com.example.supersecret.ui.theme.SuperSecretAppDontOpen

class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<FlowSummatorVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperSecretAppDontOpen {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    FlowSummator(viewModel, viewModel.itemsState, viewModel.textState)
                }
            }
        }
    }
}