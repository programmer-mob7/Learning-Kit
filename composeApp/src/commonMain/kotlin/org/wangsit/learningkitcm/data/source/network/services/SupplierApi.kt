package org.wangsit.learningkitcm.data.source.network.services

import org.wangsit.learningkitcm.data.source.network.model.request.CreateSupplierParams
import org.wangsit.learningkitcm.data.source.network.model.request.GetSuppliersParams
import org.wangsit.learningkitcm.data.source.network.model.response.CreateSupplierResponse
import org.wangsit.learningkitcm.data.source.network.model.response.GetSuppliersResponse

interface SupplierApi {
    suspend fun getSuppliers(params: GetSuppliersParams): GetSuppliersResponse
    suspend fun createSupplier(body: CreateSupplierParams): CreateSupplierResponse
}