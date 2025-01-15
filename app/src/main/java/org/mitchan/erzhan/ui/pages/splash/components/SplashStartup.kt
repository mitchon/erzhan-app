package org.mitchan.erzhan.ui.pages.splash.components

import android.provider.CalendarContract
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.min
import kotlinx.coroutines.delay
import org.mitchan.erzhan.R

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

    Image(
        modifier = Modifier.size(sizeAnimation.dp),
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        contentDescription = ""
    )
}
