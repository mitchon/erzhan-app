package org.mitchan.erzhan.viewmodels

import androidx.lifecycle.viewModelScope
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mitchan.erzhan.entities.AlarmsRepository
import org.mitchan.erzhan.models.AlarmsListModel
import org.mitchan.erzhan.routes.destinations.AlarmRouteDestination
import java.util.UUID

class AlarmsListViewModel(
    private val alarmsRepository: AlarmsRepository
): IViewModel<AlarmsListModel>(::AlarmsListModel) {

    fun initialize() {
        viewModelScope.launch(Dispatchers.IO) {
            val items = alarmsRepository.getAll().associateBy { it.id }

            stateFlow.update {
                it.copy(items = items)
            }
        }
    }

    fun enableToggled(id: UUID) {
        viewModelScope.launch(Dispatchers.IO) {
            stateFlow.value.items[id]?.let { item ->
                stateFlow.update {
                    it.copy(
                        items = it.items + (item.id to item.copy(enabled = !item.enabled))
                    )
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