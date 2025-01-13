package org.mitchan.erzhan.ui.pages.alarmslist

import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mitchan.erzhan.data.AlarmsRepository
import org.mitchan.erzhan.ui.pages.destinations.AlarmRouteDestination
import org.mitchan.erzhan.data.IViewModel
import java.util.UUID

class AlarmsListViewModel(
    private val alarmsRepository: AlarmsRepository
): IViewModel<AlarmsListModel>(::AlarmsListModel) {

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
                val newItem = alarmsRepository.updateEnabled(item.id, !item.enabled)
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