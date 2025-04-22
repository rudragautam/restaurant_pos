package com.orderpos.base

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.orderpos.data.local.AppDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class FeedbackApplication : Application() , LifecycleObserver {
    private val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }
}