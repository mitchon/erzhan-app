package org.mitchan.erzhan.ui.alarm

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.mitchan.erzhan.data.AppViewModelsProvider
import java.util.UUID

@RootNavGraph
@Destination
@Composable
fun AlarmRoute(
    id: UUID?,
    navigator: DestinationsNavigator,
    viewModel: AlarmViewModel = viewModel(factory = AppViewModelsProvider.Factory),
) {
    val state = viewModel.observe().collectAsStateWithLifecycle()

    if (!state.value.isInitialized)
        viewModel.initialize(id)

    //i don't know what to do with saveable inside:(
    if (state.value.isInitialized)
        AlarmView(
            state = state,
            onAdd = {
                viewModel.add(it)
                navigator.popBackStack()
            },
            onCancel = { navigator.popBackStack() }
        )
}