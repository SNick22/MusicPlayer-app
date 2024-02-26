package com.azat.presentation

abstract class MviViewModel<E>: BaseViewModel() {

    abstract fun onEvent(event: E)
}
