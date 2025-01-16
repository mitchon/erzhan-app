package org.mitchan.erzhan.ui.pages.alarm

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmView(
    modifier: Modifier = Modifier,
    state: AlarmModel,
    onAdd: (AlarmModel) -> Unit,
    onCancel: () -> Unit,
    onAddBarcode: () -> Unit,
) {
    Scaffold (
        modifier = modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text("Erzhan") }) }
    ) { innerPadding ->
        Crossfade(
            targetState = state.isInitialized,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            label = ""
        ) {
            if (it) {
                val timePickerState = rememberTimePickerState(
                    initialHour = state.time.hour,
                    initialMinute = state.time.minute,
                    is24Hour = true,
                )

                Column(
                    modifier = modifier.fillMaxSize()
                ) {
                    TimeInput(state = timePickerState)

                    Button(
                        onClick = onAddBarcode
                    ) {
                        Text("Add barcode")
                    }

                    Button(
                        onClick = {
                            val time = LocalTime.of(timePickerState.hour, timePickerState.minute)
                            onAdd(state.copy(time = time)) //TODO
                        }
                    ) {
                        Text("Add")
                    }

                    Button(
                        onClick = onCancel
                    ) {
                        Text("Cancel")
                    }

                }
            } else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}