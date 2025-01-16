package org.mitchan.erzhan.ui.pages.alarmslist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.mitchan.erzhan.domain.database.model.alarm.Alarm
import org.mitchan.erzhan.ui.pages.alarmslist.component.AlarmItem
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmsListView(
    modifier: Modifier = Modifier,
    state: State<AlarmsListModel>,
    onEdit: (UUID) -> Unit,
    onAdd: () -> Unit,
    onEnableToggled: (UUID) -> Unit,
    onDelete: (UUID) -> Unit,
) {
    var items by remember { mutableStateOf(emptyList<Alarm>()) }

    LaunchedEffect(state.value.items) {
        items = state.value.items.values
            .sortedBy { it.time }
            .sortedByDescending { it.enabled }
    }

    Scaffold (
        modifier = modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text("Erzhan") }) }
    ) { innerPadding ->
       Box(
           modifier = Modifier
               .fillMaxSize()
               .padding(innerPadding)
       ) {
           LazyColumn(
               verticalArrangement = Arrangement.spacedBy(4.dp),
               state = rememberLazyListState()
           ) {
               items(
                   items = items
               ) {
                   AlarmItem(
                       modifier = Modifier.animateItem(),
                       alarm = it,
                       onEnableToggled = onEnableToggled,
                       onEdit = onEdit,
                       onDelete = onDelete
                   )
               }
           }
           FloatingActionButton (
               modifier = Modifier
                   .align(Alignment.BottomEnd)
                   .absolutePadding(right = 4.dp, bottom = 4.dp),
               onClick = {
                   onAdd()
               }
           ) {
               Icon(Icons.Filled.Add, "Add alarm")
           }
       }
    }
}
