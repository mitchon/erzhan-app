package org.mitchan.erzhan.ui.pages.camera

import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CameraView() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val scope = rememberCoroutineScope()

    var cameraProvider by remember { mutableStateOf<ProcessCameraProvider?>(null) }
    var cameraSelector by remember { mutableStateOf<CameraSelector?>(null) }

    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {
            cameraProvider = ProcessCameraProvider.getInstance(context)
                .get()

            cameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            cameraProvider?.unbindAll()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Crossfade(
            targetState = (cameraProvider != null && cameraSelector != null),
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            label = ""
        ) {
            if (it) {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { context ->
                        val previewView = PreviewView(context)
                        val preview = Preview.Builder().build()
                        val selector = CameraSelector.Builder()
                            .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                            .build()

                        preview.surfaceProvider = previewView.surfaceProvider

                        runCatching {
                            cameraProvider?.bindToLifecycle(
                                lifecycleOwner,
                                selector,
                                preview
                            )
                        }.onFailure { }
                        previewView
                    }
                )
            } else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }

}
