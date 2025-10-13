package org.wangsit.learningkitcm.data.di

import org.koin.dsl.module
import org.wangsit.learningkitcm.ui.screen.createSupplier.CreateEditViewModel
import org.wangsit.learningkitcm.ui.screen.home.HomeViewModel

val appModule = module {
    factory { CreateEditViewModel(get()) }
    factory { HomeViewModel(get()) }
}
