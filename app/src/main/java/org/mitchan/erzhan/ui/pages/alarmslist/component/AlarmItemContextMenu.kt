package org.mitchan.erzhan.ui.pages.alarmslist.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.mitchan.erzhan.ui.pages.alarmslist.AlarmListItemModel
import java.util.UUID

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
