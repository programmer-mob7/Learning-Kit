package org.wangsit.learningkitcm

import android.app.Application
import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.wangsit.learningkitcm.data.di.appModule
import org.wangsit.learningkitcm.data.di.supplierModule
import org.wangsit.learningkitcm.di.commonModule   // Pastikan path import ini benar
import org.wangsit.learningkitcm.di.initKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Napier.base(DebugAntilog())
        initKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(
                appModule,
            )
        }
        Napier.i("✅ MainApplication: Napier and Koin initialized successfully!", tag = "AppLifecycle")
    }
}