package org.mitchan.erzhan.routes

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.util.UUID

@RootNavGraph
@Destination
@Composable
fun AlarmRoute(
    id: UUID?,
    navigator: DestinationsNavigator,
) {
    Text(id.toString())
}