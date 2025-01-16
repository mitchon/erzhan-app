package org.mitchan.erzhan.ui.pages.camera

import android.Manifest
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.mitchan.erzhan.ui.component.PermissionGranter

@RootNavGraph
@Destination
@Composable
fun CameraRoute(
    navigator: DestinationsNavigator,
) {
    PermissionGranter(
        arrayOf(Manifest.permission.CAMERA)
    ) {
        CameraView()
    }
}