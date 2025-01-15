package org.mitchan.erzhan.ui.pages.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.mitchan.erzhan.ui.pages.splash.components.SplashLoop
import org.mitchan.erzhan.ui.pages.splash.components.SplashStartup

private const val DURATION_START = 2000
private const val DURATION_LOOP = 2000
private const val MIN_SIZE = 240F
private const val MAX_SIZE = 300F

@Composable
fun SplashView(
    state: SplashModel,
    onAnimationDone: () -> Unit = {},
) {
    Scaffold { scaffoldPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding),
            contentAlignment = Alignment.Center
        ) {
            if (state.isAnimationDone) {
                SplashLoop(
                    duration = DURATION_START,
                    maxSize = MAX_SIZE,
                    minSize = MIN_SIZE,
                )
            } else {
                SplashStartup(
                    duration = DURATION_LOOP,
                    maxSize = MAX_SIZE,
                    minSize = MIN_SIZE,
                    onAnimationDone = onAnimationDone,
                )
            }
        }
    }
}

