package org.mitchan.erzhan.ui.pages.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator


@RootNavGraph
@Destination
@Composable
fun SplashRoute(
    navigator: DestinationsNavigator,
    viewModel: SplashViewModel = viewModel()
) {
    val state by viewModel.observe().collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.initialize(context)
    }

    LaunchedEffect(state.isInitialized, state.isAnimationDone) {
        viewModel.navigate(navigator)
    }

    SplashView(
        state,
        onAnimationDone = viewModel::animationDone
    )
}