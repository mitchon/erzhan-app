package org.mitchan.erzhan.ui.pages.alarmslist.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.mitchan.erzhan.ui.pages.alarm.AlarmModel
import org.mitchan.erzhan.ui.pages.alarmslist.AlarmListItemModel
import org.mitchan.erzhan.ui.theme.ErzhanTheme
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.UUID
import kotlin.random.Random

@Composable
fun AlarmItem(
    modifier: Modifier = Modifier,
    alarm: AlarmListItemModel,
    onEnableToggled: (id: UUID) -> Unit,
    onClick: (id: UUID) -> Unit,
    onDelete: (id: UUID) -> Unit
) {
    val alarmState by remember(alarm) { mutableStateOf(alarm) }
    Card(
        modifier = modifier.fillMaxWidth().clickable { onClick(alarm.id) },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Start,
            ) {
                Column {
                    val traits = when (val alarmTrait = alarmState.trait) {
                        is AlarmModel.TraitOnce -> buildAnnotatedString {  }
                        is AlarmModel.TraitEveryday -> buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(DayOfWeek.entries.joinToString(" ", "", " ") { it.name.first().toString() })
                            }
                        }
                        is AlarmModel.TraitByWeekday -> buildAnnotatedString {
                            DayOfWeek.entries.map { day ->
                                if (alarmTrait.weekDayMap[day] == true) {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append(day.name.first())
                                    }
                                } else
                                    append(day.name.first())
                                append(" ")
                            }
                        }
                    }
                    Text(
                        text = traits,
                    )
                    Text(
                        text = alarmState.time.format(DateTimeFormatter.ofPattern("HH:mm")),
                        fontSize = 45.sp
                    )
                }
            }
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.End,
            ) {
                Column {
                    Switch(
                        checked = alarmState.enabled,
                        onCheckedChange = { onEnableToggled(alarmState.id) }
                    )
                    AlarmsListItemContextMenu(
                        alarm = alarmState,
                        onDelete = { onDelete(alarmState.id) }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun AlarmItemPreview() {
    ErzhanTheme {
        AlarmItem (
            alarm = AlarmListItemModel(
                id = UUID.randomUUID(),
                time = LocalTime.now() + Duration.ofMinutes(Random.nextLong() % 10 ),
                enabled = true,
                trait = AlarmModel.TraitByWeekday(
                    weekDayMap = mapOf(
                        DayOfWeek.MONDAY to true,
                        DayOfWeek.TUESDAY to false,
                        DayOfWeek.WEDNESDAY to true,
                    )
                )
            ),
            onEnableToggled = { },
            onClick = { },
            onDelete = { }
        )
    }
}
