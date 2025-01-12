package org.mitchan.erzhan.viewmodels

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mitchan.erzhan.entities.AlarmsRepository
import org.mitchan.erzhan.models.AlarmModel
import java.util.UUID

class AlarmViewModel(
    private val alarmsRepository: AlarmsRepository
): IViewModel<AlarmModel>(::AlarmModel) {
    private val _initialized = MutableStateFlow(false)
    val initialized = _initialized.asStateFlow()

    fun initialize(id: UUID?) {
        viewModelScope.launch(Dispatchers.IO) {
            val model = id?.let { alarmsRepository.getById(id) }

            stateFlow.update { model ?: AlarmModel() }
            _initialized.update { true }
        }
    }

    fun add(model: AlarmModel) {
        viewModelScope.launch(Dispatchers.IO) {
            alarmsRepository.insert(model)
        }
    }
}