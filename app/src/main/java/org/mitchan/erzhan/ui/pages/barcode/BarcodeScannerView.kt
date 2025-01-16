package org.mitchan.erzhan.ui.pages.barcode

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.mlkit.vision.barcode.common.Barcode
import org.mitchan.erzhan.ui.component.BarcodeScannerComponent

@Composable
fun BarcodeScannerView(
    onUpdate: (List<Barcode>) -> Unit = {},
) {
    Scaffold { innerPadding ->
        BarcodeScannerComponent(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            onUpdate = onUpdate
        )
    }
}