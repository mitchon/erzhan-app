package org.mitchan.erzhan.ui.component

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.common.Barcode
import org.mitchan.erzhan.domain.service.BarcodeService

@Composable
fun BarcodeScannerComponent(
    modifier: Modifier = Modifier,
    onUpdate: (List<Barcode>) -> Unit = {},
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        CameraComponent(
            analyzerCallback = { executor ->
                val analyzerOptions = BarcodeScannerOptions.Builder()
                    .setExecutor(executor)
                    .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
                    .build()

                val analyzer = BarcodeService.createBarcodeAnalyzer(analyzerOptions, onUpdate)

                val resolutionSelector = ResolutionSelector
                    .Builder()
                    .setAllowedResolutionMode(ResolutionSelector.PREFER_HIGHER_RESOLUTION_OVER_CAPTURE_RATE)
                    .build()

                val imageAnalysis = ImageAnalysis.Builder()
                    .setResolutionSelector(resolutionSelector)
                    .setImageQueueDepth(1)
                    .setBackgroundExecutor(executor)
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()

                imageAnalysis.setAnalyzer(executor, analyzer)
                imageAnalysis
            }
        )
//        Icon()
    }
}