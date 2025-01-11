package org.mitchan.erzhan.routes

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.mitchan.erzhan.ui.AlarmView
import org.mitchan.erzhan.viewmodels.AlarmViewModel
import java.util.UUID

@RootNavGraph
@Destination
@Composable
fun AlarmRoute(
    id: UUID?,
    navigator: DestinationsNavigator,
    viewModel: AlarmViewModel = viewModel(),
) {

    viewModel.initialize(id)
    val state = viewModel.observe().collectAsStateWithLifecycle()

    val initialized = viewModel.initialized.collectAsStateWithLifecycle().value

    if (initialized)
        AlarmView(
            state = state,
            onAdd = {
                viewModel.add(it)
                navigator.popBackStack()
            },
            onCancel = { navigator.popBackStack() }
        )
}