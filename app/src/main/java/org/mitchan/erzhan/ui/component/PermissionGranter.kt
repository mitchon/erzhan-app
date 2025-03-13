package org.mitchan.erzhan.ui.component

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PermissionGranter(
    permissions: Array<String>,
    content: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()
    var hasUncheckedPermissions by remember { mutableStateOf<Boolean>(true) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        scope.launch(Dispatchers.IO) {
            delay(1000)
            hasUncheckedPermissions = permissionsMap.values.any { false }
        }
    }

    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {
            if (hasUncheckedPermissions) {
                permissionLauncher.launch(permissions)
            }
        }
    }

    Crossfade(
        targetState = !hasUncheckedPermissions,
        animationSpec = tween(durationMillis = 1000)
    ) {
        if (it)
            content()
        else
            Scaffold { innerPadding ->
                Box(
                    modifier = Modifier.fillMaxSize().padding(innerPadding),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
    }
}