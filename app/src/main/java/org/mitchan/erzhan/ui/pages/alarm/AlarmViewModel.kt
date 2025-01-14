package org.mitchan.erzhan.ui.pages.alarm

import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mitchan.erzhan.data.AlarmsRepository
import org.mitchan.erzhan.data.AlarmsRepositoryImpl
import org.mitchan.erzhan.data.IViewModel
import org.mitchan.erzhan.ui.pages.NavGraphs
import org.mitchan.erzhan.ui.pages.destinations.AlarmsListRouteDestination
import java.util.UUID

class AlarmViewModel: IViewModel<AlarmModel>(::AlarmModel) {
    private val alarmsRepository: AlarmsRepository = AlarmsRepositoryImpl()

    fun initialize(id: UUID?) {
        viewModelScope.launch(Dispatchers.IO) {
            val model = id?.let { alarmsRepository.getById(id) } ?: AlarmModel()

            stateFlow.update { model.copy(isInitialized = true) }
        }
    }

    fun add(model: AlarmModel) {
        viewModelScope.launch(Dispatchers.IO) {
            alarmsRepository.insert(model)
        }
    }

    fun navigateBack(navigator: DestinationsNavigator) {
        navigator.navigate(AlarmsListRouteDestination) {
            popUpTo(NavGraphs.root)
        }
    }

}