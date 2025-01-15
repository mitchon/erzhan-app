package org.mitchan.erzhan.ui.pages.root

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.mitchan.erzhan.domain.singleton.AppServiceSingleton
import org.mitchan.erzhan.domain.singleton.RoomSingleton
import org.mitchan.erzhan.ui.pages.NavGraphs
import org.mitchan.erzhan.ui.pages.destinations.SplashRouteDestination

class RootViewModel: ViewModel() {
    fun redirect(navigator: DestinationsNavigator) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!AppServiceSingleton.isInitialized || !RoomSingleton.isInitialized) {
                withContext(Dispatchers.Main) {
                    navigator.navigate(SplashRouteDestination) {
                        popUpTo(NavGraphs.root)
                    }
                }
            }
        }
    }
}