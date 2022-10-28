package com.example.themoviedb.common.utils

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    val main:CoroutineDispatcher
    val IO : CoroutineDispatcher
    val default: CoroutineDispatcher
}