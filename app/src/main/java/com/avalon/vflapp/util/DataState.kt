package com.avalon.vflapp.util

import java.lang.Exception

sealed class DataState<out R> {

    data class Success<out T>(val data: T): DataState<T>()

    data class Error(val exception: Exception): DataState<Nothing>()

    data class Cancel(val time: Int = -1): DataState<Nothing>()

    object Loading: DataState<Nothing>()
}