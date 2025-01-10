package org.mitchan.erzhan.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.mitchan.erzhan.models.AlarmListItemModel
import org.mitchan.erzhan.models.AlarmsListModel
import org.mitchan.erzhan.ui.theme.ErzhanTheme
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.UUID
import kotlin.random.Random

@Composable
fun AlarmsListView(
    modifier: Modifier = Modifier,
    state: State<AlarmsListModel>,
    onClick: (id: UUID) -> Unit,
    onAdd: () -> Unit,
    onEnableToggled: (id: UUID) -> Unit,
    onDelete: (id: UUID) -> Unit,
) {
    var items by remember { mutableStateOf(emptyList<AlarmListItemModel>()) }

    val lazyColumnState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(state.value.items) {
        items = state.value.items.values.sortedBy { it.time }
    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            state = lazyColumnState
        ) {
            items(
                items = items
            ) {
                AlarmItem(
                    alarm = it,
                    onEnableToggled = onEnableToggled,
                    onClick = onClick,
                    onDelete = onDelete
                )
            }
        }
        SmallFloatingActionButton (
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .absolutePadding(right = 4.dp, bottom = 4.dp),
            onClick = {
                onAdd()
                coroutineScope.launch(Dispatchers.IO) {
                    lazyColumnState.scrollToItem(maxOf(items.size, 0))
                }
            }
        ) {
            Icon(Icons.Filled.Add, "Add alarm")
        }
    }
}

@Composable
fun AlarmsListItemContextMenu(
    modifier: Modifier = Modifier,
    alarm: AlarmListItemModel,
    onDelete: (id: UUID) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier,
    ) {
        IconButton(onClick = { expanded = true }) {
            Icon(Icons.Default.MoreVert, contentDescription = "Show context menu")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                modifier = Modifier.padding(4.dp),
                text = { Text("Edit") },
                onClick = { }
            )
            DropdownMenuItem(
                modifier = Modifier.padding(4.dp),
                text = { Text("Delete") },
                onClick = {
                    onDelete(alarm.id)
                    expanded = false
                }
            )
        }
    }
}

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
                        is AlarmListItemModel.TraitOnce -> buildAnnotatedString {  }
                        is AlarmListItemModel.TraitEveryday -> buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(DayOfWeek.entries.joinToString(" ", "", " ") { it.name.first().toString() })
                            }
                        }
                        is AlarmListItemModel.TraitByWeekday -> buildAnnotatedString {
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
                trait = AlarmListItemModel.TraitByWeekday(
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