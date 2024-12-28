package org.mitchan.erzhan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import kotlinx.coroutines.launch
import org.mitchan.erzhan.model.AlarmListItem
import org.mitchan.erzhan.ui.theme.ErzhanTheme
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.UUID

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ErzhanTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(title = { Text("Erzhan") })
                    }
                ) { innerPadding ->
                    AlarmsListScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun AlarmsListScreen(modifier: Modifier = Modifier) {
    var alarmsMap by remember { mutableStateOf<Map<UUID, AlarmListItem>>(emptyMap()) }
    val lazyColumnState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

//    LaunchedEffect(alarmsMap) {
//        alarmsMap = alarmsMap.entries
//            .sortedBy { (_, v) -> v.time }
//            .sortedBy { (_, v) -> v.enabled }
//            .associate { (k, v) -> k to v }
//    }

    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            state = lazyColumnState
        ) {
            items(
                items = alarmsMap.entries.toList()
            ) {
                AlarmItem(
                    alarm = it.value,
                    onEnableChanged = { alarm ->
                        alarmsMap[alarm.id]?.let {
                            alarmsMap += it.id to it.copy(enabled = !it.enabled)
                        }
                    },
                    onDelete = { id ->
                        alarmsMap = alarmsMap.minus(id)
                    }
                )
            }
        }
        SmallFloatingActionButton (
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .absolutePadding(right = 4.dp, bottom = 4.dp),
            onClick = {
                val new = AlarmListItem(
                    id = UUID.randomUUID(),
                    time = LocalTime.now(),
                    enabled = true,
                    trait = AlarmListItem.TraitByWeekday(
                        weekDayMap = mapOf(
                            DayOfWeek.MONDAY to true,
                            DayOfWeek.TUESDAY to false,
                            DayOfWeek.WEDNESDAY to true,
                        )
                    )
                )
                alarmsMap += (new.id to new)
                coroutineScope.launch {
                    lazyColumnState.scrollToItem(alarmsMap.size - 1)
                }
            }
        ) {
            Icon(Icons.Filled.Add, "Add alarm")
        }
    }
}

@Composable
fun AlarmContextMenu(
    modifier: Modifier = Modifier,
    alarm: AlarmListItem,
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
                onClick = { onDelete(alarm.id) }
            )
        }
    }
}

@Composable
fun AlarmItem(
    modifier: Modifier = Modifier,
    alarm: AlarmListItem,
    onEnableChanged: (alarm: AlarmListItem) -> Unit,
    onDelete: (id: UUID) -> Unit
) {
    val alarmState by remember(alarm) { mutableStateOf(alarm) }
    Card(
        modifier = modifier.fillMaxWidth()
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
                        is AlarmListItem.TraitOnce -> buildAnnotatedString {  }
                        is AlarmListItem.TraitEveryday -> buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(DayOfWeek.entries.joinToString(" ", "", " ") { it.name.first().toString() })
                            }
                        }
                        is AlarmListItem.TraitByWeekday -> buildAnnotatedString {
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
                        onCheckedChange = { onEnableChanged(alarmState) }
                    )
                    AlarmContextMenu(
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
            alarm = AlarmListItem(
                id = UUID.randomUUID(),
                time = LocalTime.now(),
                enabled = true,
                trait = AlarmListItem.TraitByWeekday(
                    weekDayMap = mapOf(
                        DayOfWeek.MONDAY to true,
                        DayOfWeek.TUESDAY to false,
                        DayOfWeek.WEDNESDAY to true,
                    )
                )
            ),
            onEnableChanged = { },
            onDelete = { }
        )
    }
}