package org.mitchan.erzhan.ui.alarm

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mitchan.erzhan.data.AlarmsRepository
import org.mitchan.erzhan.data.IViewModel
import java.util.UUID

class AlarmViewModel(
    private val alarmsRepository: AlarmsRepository
): IViewModel<AlarmModel>(::AlarmModel) {

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
}