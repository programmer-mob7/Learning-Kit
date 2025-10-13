package org.wangsit.learningkitcm.data.repository

import org.wangsit.learningkitcm.data.source.network.dataSource.SupplierApiDataSource
import org.wangsit.learningkitcm.data.source.network.model.request.CreateSupplierParams
import org.wangsit.learningkitcm.data.source.network.model.request.GetSuppliersParams
import org.wangsit.learningkitcm.data.source.network.model.response.CreateSupplierResponse
import org.wangsit.learningkitcm.data.source.network.model.response.GetSuppliersResponse

class SupplierRepositoryImpl(
    private val dataSource: SupplierApiDataSource
) : SupplierRepository {

    override suspend fun getSuppliers(params: GetSuppliersParams): GetSuppliersResponse {
        return dataSource.getSuppliers(params)
    }

    override suspend fun createSupplier(body: CreateSupplierParams): CreateSupplierResponse {
        return dataSource.createSupplier(body)
    }
}