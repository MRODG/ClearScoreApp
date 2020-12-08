package com.mariosodigie.apps.clearscoreapp.api

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mariosodigie.apps.clearscoreapp.R

enum class ApiError(@StringRes val title: Int, @StringRes val message: Int, @DrawableRes val icon: Int? = null) {

    Generic(R.string.error_title_generic, R.string.error_message_generic),
    PhoneOffline(R.string.phone_error_title, R.string.phone_error_message, R.drawable.ic_wifi_off_black_18dp),
    CreditFileNotFound(R.string.credit_file_not_found_title, R.string.credit_file_not_found_message)
}