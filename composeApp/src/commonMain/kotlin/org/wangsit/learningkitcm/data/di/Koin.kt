package org.wangsit.learningkitcm.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.wangsit.learningkitcm.data.di.supplierModule

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(supplierModule)
}

fun initKoin() = initKoin {}
