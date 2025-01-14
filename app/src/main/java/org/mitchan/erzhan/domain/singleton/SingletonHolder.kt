package org.mitchan.erzhan.domain.singleton

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import org.mitchan.erzhan.domain.model.ISingleton

abstract class SingletonHolder<out T : ISingleton, in Arg>(
    private val creator: (Arg) -> T,
) {
    private val instance: MutableStateFlow<T?> = MutableStateFlow(null)

    val isInitialized: Boolean
        get() {
            return instance.value != null
        }

    fun getInstance(arg: Arg): T {
        return instance.value ?: synchronized(this) {
            creator(arg).also { new ->
                instance.update { new }
            }
        }
    }

    fun getInstanceUnsafe(): T {
        return instance.value ?: throw Exception("$TAG - getInstanceUnsafe: not initialized")
    }
}