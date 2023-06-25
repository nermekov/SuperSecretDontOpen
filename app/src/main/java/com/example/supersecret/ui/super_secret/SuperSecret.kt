package com.example.supersecret.ui.super_secret

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.supersecret.UIActions
import com.example.supersecret.R
import com.example.supersecret.UiRow

@Composable
fun SuperSecret(
    uiActions: UIActions,
    itemsState: SnapshotStateList<UiRow>,
    textState: MutableState<String>
) {
    Column {
        val rTextState = remember {
            textState
        }
        TextField(value = rTextState.value, onValueChange = { textState.value = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            placeholder = { Text(text = stringResource(R.string.input_hint)) })
        Button(
            onClick = { uiActions.onSubmitClicked() },
        ) {
            Text(text = stringResource(R.string.submit))
        }
        val rItemsState = remember { itemsState }
        LazyColumn {
            items(rItemsState) {
                Text(
                    text = stringResource(
                        R.string.row_content, it.n.toString(), it.calculatedValues.joinToString(
                            " "
                        )
                    )
                )
            }
        }
    }
}