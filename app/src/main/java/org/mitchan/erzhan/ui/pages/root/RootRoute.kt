package org.mitchan.erzhan.ui.pages.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun RootRoute(
    navigator: DestinationsNavigator,
    viewModel: RootViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.redirect(navigator)
    }

    Scaffold { innerPadding -> Box(modifier = Modifier.padding(innerPadding)) }
}