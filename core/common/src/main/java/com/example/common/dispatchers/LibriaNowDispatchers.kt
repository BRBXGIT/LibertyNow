package com.example.common.dispatchers

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val libriaNowDispatcher: LibriaNowDispatchers)

enum class LibriaNowDispatchers {
    Default,
    IO,
}