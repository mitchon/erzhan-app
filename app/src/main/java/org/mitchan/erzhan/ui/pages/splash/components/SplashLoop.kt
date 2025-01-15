package org.mitchan.erzhan.ui.pages.splash.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.mitchan.erzhan.ui.icons.ErzhanIcons
import org.mitchan.erzhan.ui.icons.Logo

@Composable
fun SplashLoop(
    duration: Int,
    minSize: Float,
    maxSize: Float,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val sizeAnimation by infiniteTransition.animateFloat(
        initialValue = maxSize,
        targetValue = minSize,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = duration),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    Icon(
        modifier = Modifier.size(sizeAnimation.dp),
        imageVector = ErzhanIcons.Logo,
        contentDescription = ""
    )
}