package org.mitchan.erzhan.ui.pages.alarm

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
    val state by viewModel.observe().collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
      viewModel.initialize(id)
    }

    AlarmView(
        state = state,
        onAdd = {
            viewModel.add(it)
            viewModel.navigateBack(navigator)
        },
        onCancel = { viewModel.navigateBack(navigator) }
    )
}