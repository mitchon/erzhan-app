package org.mitchan.erzhan.ui.alarm

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmView(
    modifier: Modifier = Modifier,
    state: State<AlarmModel>,
    onAdd: (AlarmModel) -> Unit,
    onCancel: () -> Unit,
) {
    val timePickerState = rememberTimePickerState(
        initialHour = state.value.time.hour,
        initialMinute = state.value.time.minute,
        is24Hour = true,
    )

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TimeInput(state = timePickerState)

        Button(
            onClick = {
                val time = LocalTime.of(timePickerState.hour, timePickerState.minute)
                onAdd(state.value.copy(time = time))
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
}