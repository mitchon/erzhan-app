package org.mitchan.erzhan.routes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.mitchan.erzhan.ui.AlarmsListView
import org.mitchan.erzhan.viewmodels.AlarmsListViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun AlarmsListRoute(
    navigator: DestinationsNavigator,
    viewModel: AlarmsListViewModel = viewModel(),
) {
    val state = viewModel.observe().collectAsState()

    AlarmsListView(
        state = state,
        onClick = { id -> viewModel.navigateAlarm(id, navigator) },
        onAdd = { viewModel.add() },
        onEnableToggled = { id -> viewModel.enableToggled(id) },
        onDelete = { id -> viewModel.delete(id) }
    )
}