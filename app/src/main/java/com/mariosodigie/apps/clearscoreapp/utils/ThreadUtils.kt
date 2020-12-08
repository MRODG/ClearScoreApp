package com.mariosodigie.apps.clearscoreapp.utils

import android.annotation.SuppressLint
import androidx.arch.core.executor.ArchTaskExecutor

@SuppressLint("RestrictedApi")
fun runOnMainThread(run: () -> Unit) = ArchTaskExecutor.getInstance().postToMainThread(run)