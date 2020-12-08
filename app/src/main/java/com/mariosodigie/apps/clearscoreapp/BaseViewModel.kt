package com.mariosodigie.apps.clearscoreapp

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mariosodigie.apps.clearscoreapp.api.ApiError
import com.mariosodigie.apps.clearscoreapp.api.ServiceCallback
import com.mariosodigie.apps.clearscoreapp.utils.OpenForTesting
import com.mariosodigie.apps.clearscoreapp.utils.runOnMainThread

data class ServiceError(val title: String?, val message: String, val apiError: ApiError = ApiError.Generic)
@OpenForTesting
abstract class BaseViewModel(protected val context: Context): ViewModel() {

    val requestInProgress = MutableLiveData<Boolean>()
    val serviceError = MutableLiveData<ServiceError>()

    protected inner class ServiceCallbackImpl<T> (private val handler: (T) -> Unit) : ServiceCallback<T>{
        init {
            requestInProgress.value = true
        }

        override fun onSuccess(response: T) =  runOnMainThread{
            requestInProgress.value = false
            handler(response)
        }

        override fun onError(error: ApiError) {
            requestInProgress.value = false
            serviceError.value = ServiceError(context.getString(error.title), context.getString(error.message), error)
        }

        override fun onError(throwable: Throwable) {
            onError(ApiError.Generic)
        }
    }
}