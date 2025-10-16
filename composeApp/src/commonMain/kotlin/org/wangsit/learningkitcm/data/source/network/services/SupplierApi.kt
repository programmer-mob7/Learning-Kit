package org.wangsit.learningkitcm.data.source.network.services

import org.wangsit.learningkitcm.data.source.network.model.request.CreateSupplierParams
import org.wangsit.learningkitcm.data.source.network.model.request.DeleteSupplierParams
import org.wangsit.learningkitcm.data.source.network.model.request.GetSuppliersParams
import org.wangsit.learningkitcm.data.source.network.model.request.UpdateSupplierParams
import org.wangsit.learningkitcm.data.source.network.model.response.CreateSupplierResponse
import org.wangsit.learningkitcm.data.source.network.model.response.DeleteSupplierResponse
import org.wangsit.learningkitcm.data.source.network.model.response.GetSuppliersResponse
import org.wangsit.learningkitcm.data.source.network.model.response.UpdateSupplierResponse

interface SupplierApi {
    suspend fun getSuppliers(params: GetSuppliersParams): GetSuppliersResponse
    suspend fun createSupplier(body: CreateSupplierParams): CreateSupplierResponse
    suspend fun deleteSupplier(body: DeleteSupplierParams): DeleteSupplierResponse
    suspend fun updateSupplier(id: String, body: UpdateSupplierParams): UpdateSupplierResponse
}