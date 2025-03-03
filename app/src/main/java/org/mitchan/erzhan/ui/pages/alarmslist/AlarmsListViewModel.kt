package org.mitchan.erzhan.ui.pages.alarmslist

import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mitchan.erzhan.domain.repository.AlarmsRepository
import org.mitchan.erzhan.domain.repository.AlarmsRepositoryImpl
import org.mitchan.erzhan.ui.pages.destinations.AlarmRouteDestination
import org.mitchan.erzhan.domain.model.IViewModel
import java.util.UUID

class AlarmsListViewModel: IViewModel<AlarmsListModel>(::AlarmsListModel) {
    private val alarmsRepository: AlarmsRepository = AlarmsRepositoryImpl()

    fun initialize() {
        viewModelScope.launch(Dispatchers.IO) {
            val items = alarmsRepository.getAll().associateBy { it.id }

            stateFlow.update {
                it.copy(items = items, isInitialized = true)
            }
        }
    }

    fun enableToggled(id: UUID) {
        viewModelScope.launch(Dispatchers.IO) {
            stateFlow.value.items[id]?.let { item ->
                val newItem = alarmsRepository.update(item.copy(enabled = !item.enabled))
                stateFlow.update {
                    it.copy(items = it.items + (newItem.id to newItem))
                }
            }
        }
    }

    fun delete(id: UUID) {
        viewModelScope.launch(Dispatchers.IO) {
            alarmsRepository.delete(id)
            stateFlow.update {
                it.copy(
                    items = it.items - id
                )
            }
        }
    }

    fun navigateItem(id: UUID?, navigator: DestinationsNavigator) {
        navigator.navigate(AlarmRouteDestination(id))
    }
}