package org.mitchan.erzhan.ui.pages.root

import android.Manifest
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
import org.mitchan.erzhan.ui.component.PermissionGranter

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

    PermissionGranter(arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.USE_EXACT_ALARM,
//        Manifest.permission.FOREGROUND_SERVICE,
//        Manifest.permission.WAKE_LOCK,
//        Manifest.permission.DISABLE_KEYGUARD,
    )) {
        Scaffold { innerPadding -> Box(modifier = Modifier.padding(innerPadding)) }
    }
}