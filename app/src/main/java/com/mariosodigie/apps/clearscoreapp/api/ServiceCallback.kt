package com.mariosodigie.apps.clearscoreapp.api

interface ServiceCallback<T> {
    fun onSuccess(response: T)
    fun onError(error: ApiError)
    fun onError(throwable: Throwable)
}