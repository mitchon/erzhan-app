package org.mitchan.erzhan.domain.service

import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

object BarcodeService {

    fun createBarcodeAnalyzer(
        options: BarcodeScannerOptions,
        onScanResult: (
            List<Barcode>,
        ) -> Unit,
    ): ImageAnalysis.Analyzer = BarcodeAnalyzer(options, onScanResult)

    private class BarcodeAnalyzer(
        options: BarcodeScannerOptions,
        val onScanResult: (List<Barcode>) -> Unit,
    ) : ImageAnalysis.Analyzer {
        private val barcodeScanner: BarcodeScanner = BarcodeScanning.getClient(options)

        @OptIn(ExperimentalGetImage::class)
        override fun analyze(imageProxy: ImageProxy) {
            val mediaImage = imageProxy.image ?: return

            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
            barcodeScanner.process(image)
                .addOnSuccessListener { barcodes ->
                    if (barcodes.isNotEmpty()) onScanResult(barcodes)
                    imageProxy.close()
                }
                .addOnFailureListener {
                    imageProxy.close()
                }
        }
    }
}
