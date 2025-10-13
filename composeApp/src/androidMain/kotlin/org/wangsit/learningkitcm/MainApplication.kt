package org.wangsit.learningkitcm

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.wangsit.learningkitcm.data.di.appModule
import org.wangsit.learningkitcm.data.di.supplierModule
import org.wangsit.learningkitcm.di.commonModule   // Pastikan path import ini benar

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(
                commonModule,
                appModule,
                supplierModule
            )
        }
    }
}