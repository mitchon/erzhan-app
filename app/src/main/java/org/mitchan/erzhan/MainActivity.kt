package org.mitchan.erzhan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import com.ramcosta.composedestinations.DestinationsNavHost
import org.mitchan.erzhan.ui.NavGraphs
import org.mitchan.erzhan.ui.theme.ErzhanTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ErzhanTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(title = { Text("Erzhan") })
                    }
                ) { innerPadding ->
                    DestinationsNavHost(
                        navGraph = NavGraphs.root,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
