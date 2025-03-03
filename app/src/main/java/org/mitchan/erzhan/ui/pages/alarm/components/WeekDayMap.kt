package org.mitchan.erzhan.ui.pages.alarm.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.mitchan.erzhan.ui.model.Trait
import java.time.DayOfWeek

private val WEEK_DAYS = DayOfWeek.entries.toList()

private fun checkEveryday(trait: Trait): Boolean {
    return trait.everyDay || checkEveryday(trait.weekDayMap)
}
private fun checkEveryday(weekDayMap: Map<DayOfWeek, Boolean>): Boolean {
    return WEEK_DAYS.all { weekDayMap[it] == true }
}

@Composable
fun WeekDayMap(
    modifier: Modifier = Modifier,
    trait: Trait,
    onUpdate: (Trait) -> Unit
) {
    var traitState by remember { mutableStateOf(trait) }
    Column {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = checkEveryday(traitState),
                onCheckedChange = {
                    traitState = if (!traitState.everyDay)
                        traitState.copy(
                            everyDay = true,
                            weekDayMap = WEEK_DAYS.associateWith { true },
                        )
                    else
                        traitState.copy(
                            everyDay = false,
                            weekDayMap = emptyMap(),
                        )
                    onUpdate(traitState)
                }
            )
            Text("Every day")
        }
        Row(
            modifier = modifier.wrapContentSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            WEEK_DAYS.map { day ->
                FilledIconToggleButton (
                    checked = (traitState.everyDay || traitState.weekDayMap[day] == true),
                    onCheckedChange = {
                        val oldValue = traitState.weekDayMap[day] ?: false
                        val newValue = traitState.weekDayMap.toMutableMap()
                            .also { it[day] = !oldValue }

                        traitState = traitState.copy(
                            everyDay = checkEveryday(newValue),
                            weekDayMap = newValue
                        )
                        onUpdate(traitState)
                    }
                ) {
                    Text(day.name.first().toString())
                }
            }

        }
    }
}