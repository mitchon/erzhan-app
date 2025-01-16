package org.mitchan.erzhan.ui.pages.barcode

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph
@Destination
@Composable
fun BarcodeScannerRoute(
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current

    BarcodeScannerView(
        onUpdate = {
            Toast.makeText(context, it.mapNotNull { it.rawValue }.joinToString(), Toast.LENGTH_SHORT).show()
            navigator.navigateUp()
        }
    )
}