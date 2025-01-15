package org.mitchan.erzhan.ui.pages.splash.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.mitchan.erzhan.R

@Composable
fun SplashLoop(
    duration: Int,
    minSize: Float,
    maxSize: Float,
) {
    val infiniteTransition = rememberInfiniteTransition()

    val sizeAnimation by infiniteTransition.animateFloat(
        initialValue = maxSize,
        targetValue = minSize,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = duration),
            repeatMode = RepeatMode.Reverse
        )
    )

    Image(
        modifier = Modifier.size(sizeAnimation.dp),
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        contentDescription = ""
    )
}