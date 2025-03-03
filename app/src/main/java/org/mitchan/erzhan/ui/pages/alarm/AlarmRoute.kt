package org.mitchan.erzhan.ui.pages.alarm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.util.UUID

@RootNavGraph
@Destination
@Composable
fun AlarmRoute(
    id: UUID?,
    navigator: DestinationsNavigator,
    viewModel: AlarmViewModel = viewModel(),
) {
    val state by viewModel.observe().collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
      viewModel.initialize(id)
    }
    AlarmView(
        state = state,
        onAccept = {
            viewModel.upsert(it, state.isNew)
            viewModel.navigateBack(navigator)
        },
        onCancel = { viewModel.navigateBack(navigator) },
        onAddBarcode = { viewModel.navigateToCamera(navigator) }
    )
}