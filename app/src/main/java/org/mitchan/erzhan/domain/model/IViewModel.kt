package org.mitchan.erzhan.domain.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class IViewModel<T> protected constructor(factory: () -> T) : ViewModel() {
    protected val stateFlow: MutableStateFlow<T> = MutableStateFlow(factory())

    fun observe(): StateFlow<T> {
        return stateFlow.asStateFlow()
    }
}