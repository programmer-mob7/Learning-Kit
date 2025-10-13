package org.wangsit.learningkitcm.data.di

import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.dsl.module
import org.wangsit.learningkitcm.data.model.Supplier
import org.wangsit.learningkitcm.data.repository.SupplierRepository
import org.wangsit.learningkitcm.data.repository.SupplierRepositoryImpl
import org.wangsit.learningkitcm.data.source.network.dataSource.SupplierApiDataSource
import org.wangsit.learningkitcm.data.source.network.dataSource.SupplierApiDataSourceImpl
import org.wangsit.learningkitcm.data.source.network.services.SupplierApi
import org.wangsit.learningkitcm.data.source.network.services.SupplierApiImpl
import org.wangsit.learningkitcm.ui.screen.home.HomeViewModel

val supplierModule = module {
    single { HttpClientFactory().create() }
    single<SupplierApi> { SupplierApiImpl(get()) }
    single<SupplierApiDataSource> { SupplierApiDataSourceImpl(get()) }
    single<SupplierRepository> { SupplierRepositoryImpl(get()) }
}