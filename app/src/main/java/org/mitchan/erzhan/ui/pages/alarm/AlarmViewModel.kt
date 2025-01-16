package org.mitchan.erzhan.ui.pages.alarm

import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mitchan.erzhan.domain.database.model.alarm.Alarm
import org.mitchan.erzhan.domain.repository.AlarmsRepository
import org.mitchan.erzhan.domain.repository.AlarmsRepositoryImpl
import org.mitchan.erzhan.domain.model.IViewModel
import org.mitchan.erzhan.ui.model.Trait
import org.mitchan.erzhan.ui.pages.NavGraphs
import org.mitchan.erzhan.ui.pages.destinations.AlarmsListRouteDestination
import org.mitchan.erzhan.ui.pages.destinations.BarcodeScannerRouteDestination
import java.time.LocalDate
import java.util.UUID

class AlarmViewModel: IViewModel<AlarmModel>(::AlarmModel) {
    private val alarmsRepository: AlarmsRepository = AlarmsRepositoryImpl()

    fun initialize(id: UUID?) {
        viewModelScope.launch(Dispatchers.IO) {
            val model = id
                ?.let { alarmsRepository.getById(it) }
                ?.let { AlarmModel(value = it, isInitialized = true, isNew = false) }
                ?: AlarmModel(isInitialized = true)

            stateFlow.update { model }
        }
    }

    fun upsert(alarm: Alarm) {
        viewModelScope.launch(Dispatchers.IO) {
            if (alarm.id == null)
                alarmsRepository.insert(alarm)
            else
                alarmsRepository.update(alarm)
        }
    }

    fun navigateBack(navigator: DestinationsNavigator) {
        navigator.navigate(AlarmsListRouteDestination) {
            popUpTo(NavGraphs.root)
        }
    }

    fun navigateToCamera(navigator: DestinationsNavigator) {
        navigator.navigate(BarcodeScannerRouteDestination)
    }

}