package org.mitchan.erzhan.ui.pages.alarm

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.mitchan.erzhan.domain.database.model.alarm.Alarm
import org.mitchan.erzhan.ui.model.Trait
import org.mitchan.erzhan.ui.pages.alarm.components.WeekDayMap
import org.mitchan.erzhan.ui.theme.ErzhanTheme
import java.time.LocalTime
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmView(
    modifier: Modifier = Modifier,
    state: AlarmModel,
    onAccept: (Alarm) -> Unit,
    onCancel: () -> Unit,
    onAddBarcode: () -> Unit,
) {
    var alarmFormState by remember {
        mutableStateOf(state.value)
    }

    Scaffold (
        modifier = modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text("Erzhan") }) }
    ) { innerPadding ->
        Crossfade(
            targetState = state.isInitialized,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            label = ""
        ) {
            if (it) {
                alarmFormState = state.value

                val timePickerState = rememberTimePickerState(
                    initialHour = alarmFormState.time.hour,
                    initialMinute = alarmFormState.time.minute,
                    is24Hour = true,
                )

                var traitState by remember {
                    mutableStateOf(alarmFormState.trait)
                }

                Column(
                    modifier = modifier.fillMaxSize()
                ) {
                    TimeInput(state = timePickerState)

                    WeekDayMap(modifier, traitState) { traitState = it }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                    }

                    Button(
                        onClick = onAddBarcode
                    ) {
                        Text("Add barcode")
                    }

                    Button(
                        onClick = {
                            val time = LocalTime.of(timePickerState.hour, timePickerState.minute)
                            onAccept(alarmFormState.copy(time = time, trait = traitState))
                        }
                    ) {
                        if (state.isNew)
                            Text("Add")
                        else
                            Text("Edit")
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

@Preview
@Composable
fun AlarmViewPreview() {
    ErzhanTheme {
        AlarmView(
            state = AlarmModel(value = Alarm(id = UUID.randomUUID()), isInitialized = true),
            onAccept = {},
            onCancel = {},
            onAddBarcode = {},
        )
    }
}
