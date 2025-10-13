package org.wangsit.learningkitcm.di

import org.koin.dsl.module
import org.wangsit.learningkitcm.data.di.HttpClientFactory
import org.wangsit.learningkitcm.data.source.network.dataSource.SupplierApiDataSource
import org.wangsit.learningkitcm.data.source.network.dataSource.SupplierApiDataSourceImpl
import org.wangsit.learningkitcm.data.source.network.services.SupplierApi
import org.wangsit.learningkitcm.data.source.network.services.SupplierApiImpl

// Modul ini hanya berisi dependensi murni Kotlin (aman untuk semua platform)
val commonModule = module {
    single { HttpClientFactory().create() }
    single<SupplierApi> { SupplierApiImpl(get()) }
    single<SupplierApiDataSource> { SupplierApiDataSourceImpl(get()) }
}
