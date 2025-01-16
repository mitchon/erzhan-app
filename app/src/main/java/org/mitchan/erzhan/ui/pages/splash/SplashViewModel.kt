package org.mitchan.erzhan.ui.pages.splash

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mitchan.erzhan.domain.model.IViewModel
import org.mitchan.erzhan.domain.singleton.AppServiceSingleton
import org.mitchan.erzhan.domain.singleton.RoomSingleton
import org.mitchan.erzhan.ui.pages.NavGraphs
import org.mitchan.erzhan.ui.pages.destinations.AlarmsListRouteDestination

class SplashViewModel: IViewModel<SplashModel>(::SplashModel) {

    fun initialize(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!stateFlow.value.isInitialized) {
                AppServiceSingleton.getInstance(context)
                RoomSingleton.getInstance(Unit)

                stateFlow.update {
                    it.copy(
                        isInitialized = true,
                    )
                }
            }
        }
    }

    fun animationDone() {
        stateFlow.update {
            it.copy(
                isAnimationDone = true,
            )
        }
    }

    fun navigate(navigator: DestinationsNavigator) {
        if (stateFlow.value.isInitialized && stateFlow.value.isAnimationDone) {
            navigator.navigate(AlarmsListRouteDestination) {
                launchSingleTop = true
                popUpTo(NavGraphs.root)
            }
        }
    }

}