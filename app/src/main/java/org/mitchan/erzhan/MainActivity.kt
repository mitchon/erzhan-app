package org.mitchan.erzhan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.DestinationsNavHost
import org.mitchan.erzhan.domain.singleton.AppServiceSingleton
import org.mitchan.erzhan.ui.pages.NavGraphs
import org.mitchan.erzhan.ui.theme.ErzhanTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            ErzhanTheme {
                DestinationsNavHost(
                    navGraph = NavGraphs.root,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
