package org.wangsit.learningkitcm.data.source.network.dataSource

import org.wangsit.learningkitcm.data.source.network.model.request.CreateSupplierParams
import org.wangsit.learningkitcm.data.source.network.model.request.GetSuppliersParams
import org.wangsit.learningkitcm.data.source.network.model.response.CreateSupplierResponse
import org.wangsit.learningkitcm.data.source.network.model.response.GetSuppliersResponse
import org.wangsit.learningkitcm.data.source.network.services.SupplierApi

class SupplierApiDataSourceImpl (
    private val supplierApi: SupplierApi
) : SupplierApiDataSource{

    override suspend fun getSuppliers(params: GetSuppliersParams): GetSuppliersResponse {
        return supplierApi.getSuppliers(params)
    }

    override suspend fun createSupplier(body: CreateSupplierParams): CreateSupplierResponse {
        return supplierApi.createSupplier(body)
    }
}