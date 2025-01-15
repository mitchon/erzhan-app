package org.mitchan.erzhan.ui.pages.splash.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.mitchan.erzhan.ui.icons.ErzhanIcons
import org.mitchan.erzhan.ui.icons.Logo

private const val ANIMATION_PRE_WAITING_RATIO = 0.1
private const val ANIMATION_POST_WAITING_RATION = 0.1
private const val ANIMATION_MAIN_RATIO = 1.0 - ANIMATION_PRE_WAITING_RATIO - ANIMATION_POST_WAITING_RATION

@Composable
fun SplashStartup(
    duration: Int,
    minSize: Float,
    maxSize: Float,
    onAnimationDone: () -> Unit = {},
) {
    val animationPreWaiting = remember { (duration*ANIMATION_PRE_WAITING_RATIO).toLong() }
    val animationMain = remember { (duration*ANIMATION_MAIN_RATIO).toLong() }
    val animationPostWaiting = remember { (duration*ANIMATION_POST_WAITING_RATION).toLong() }


    var animateState by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(Unit) {
        delay(animationPreWaiting)
        animateState = true
        delay(animationMain)
        delay(animationPostWaiting)
        onAnimationDone()
    }

    val sizeAnimation by animateFloatAsState(
        targetValue = if (animateState) {
            maxSize
        } else {
            minSize
        },
        animationSpec = tween(durationMillis = animationMain.toInt())
    )

    Icon(
        modifier = Modifier.size(sizeAnimation.dp),
        imageVector = ErzhanIcons.Logo,
        contentDescription = ""
    )
}
