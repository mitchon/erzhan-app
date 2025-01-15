package org.mitchan.erzhan.ui.icons

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val ErzhanIcons.Logo: ImageVector
    get() {
        val current = _logo
        if (current != null) return current

        return ImageVector.Builder(
            name = "",
            defaultWidth = 100.0.dp,
            defaultHeight = 100.0.dp,
            viewportWidth = 100.0f,
            viewportHeight = 100.0f,
        ).apply {
            path(
                fill = SolidColor(Color(0xFFFFFFFF)),
            ) {
                moveTo(x = 15f, y = 40f)
                lineToRelative(dx = 20f, dy = -15f)
                lineToRelative(dx = 30f, dy = 0f)
                lineToRelative(dx = 20f, dy = 15f)
                lineToRelative(dx = 0f, dy = 15f)
                arcToRelative(a = 1f, b = 1f, theta = 0f, isMoreThanHalf = false, isPositiveArc = true, dx1 = -70f, dy1 = 0f)
                lineToRelative(dx = 0f, dy = -15f)

                moveTo(x = 13f, y = 38f)
                arcToRelative(a = 1f, b = 1f, theta = 0f, isMoreThanHalf = false, isPositiveArc = true, dx1 = 20f, dy1 = -15f)
                moveToRelative(dx = 34f, dy = 0f)
                arcToRelative(a = 1f, b = 1f, theta = 0f, isMoreThanHalf = false, isPositiveArc = true, dx1 = 20f, dy1 = 15f)
            }
        }.build().also { _logo = it }
    }

@Suppress("ObjectPropertyName")
private var _logo: ImageVector? = null

@Preview
@Composable
private fun IconPreview() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            imageVector = ErzhanIcons.Logo,
            contentDescription = null,
            modifier = Modifier
                .width((108.0).dp)
                .height((108.0).dp),
        )
    }
}