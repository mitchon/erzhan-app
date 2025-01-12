package org.mitchan.erzhan.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.mitchan.erzhan.ErzhanApplication

abstract class IViewModel<T> protected constructor(factory: () -> T) : ViewModel() {
    protected val stateFlow: MutableStateFlow<T> = MutableStateFlow(factory())

    fun observe(): StateFlow<T> {
        return stateFlow.asStateFlow()
    }
}

object AppViewModelsProvider {
    val Factory = viewModelFactory {
        initializer {
            AlarmViewModel(
                erzhanApplication().container.alarmsRepository
            )
        }

        initializer {
            AlarmsListViewModel(
                erzhanApplication().container.alarmsRepository
            )
        }
    }
}

fun CreationExtras.erzhanApplication(): ErzhanApplication = (this[AndroidViewModelFactory.APPLICATION_KEY] as ErzhanApplication)