package org.mitchan.erzhan.ui.pages.alarmslist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph
@Destination
@Composable
fun AlarmsListRoute(
    navigator: DestinationsNavigator,
    viewModel: AlarmsListViewModel = viewModel(),
) {
    val state = viewModel.observe().collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    AlarmsListView (
        state = state,
        onClick = { id -> viewModel.navigateItem(id, navigator) },
        onAdd = { viewModel.navigateItem(null, navigator) },
        onEnableToggled = { id -> viewModel.enableToggled(id) },
        onDelete = { id -> viewModel.delete(id) }
    )
}