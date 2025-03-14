package org.mitchan.erzhan.ui.component

import android.util.Log
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
    var allGranted by remember { mutableStateOf<Boolean>(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        scope.launch(Dispatchers.IO) {
            permissionsMap.forEach { (permission, granted) ->
                if (granted)
                    Log.d("erzhan_permissions", "$permission granted")
                else
                    Log.d("erzhan_permissions", "$permission not granted")
            }
            delay(1000)
            allGranted = permissionsMap.all { it.value }
        }
    }

//    LaunchedEffect(allGranted) {
//        scope.launch(Dispatchers.IO) {
//            if (!allGranted) {
//                permissionLauncher.launch(permissions)
//            }
//        }
//    }

    Crossfade(
        targetState = allGranted,
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
                    permissionLauncher.launch(permissions)
                }
            }
    }
}