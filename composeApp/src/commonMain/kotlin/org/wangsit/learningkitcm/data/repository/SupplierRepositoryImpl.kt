package org.wangsit.learningkitcm.data.repository

import org.wangsit.learningkitcm.data.source.network.dataSource.SupplierApiDataSource
import org.wangsit.learningkitcm.data.source.network.model.request.CreateSupplierParams
import org.wangsit.learningkitcm.data.source.network.model.request.DeleteSupplierParams
import org.wangsit.learningkitcm.data.source.network.model.request.GetSuppliersParams
import org.wangsit.learningkitcm.data.source.network.model.request.UpdateSupplierParams
import org.wangsit.learningkitcm.data.source.network.model.response.CreateSupplierResponse
import org.wangsit.learningkitcm.data.source.network.model.response.DeleteSupplierResponse
import org.wangsit.learningkitcm.data.source.network.model.response.GetSuppliersResponse
import org.wangsit.learningkitcm.data.source.network.model.response.UpdateSupplierResponse

class SupplierRepositoryImpl(
    private val dataSource: SupplierApiDataSource
) : SupplierRepository {

    override suspend fun getSuppliers(params: GetSuppliersParams): GetSuppliersResponse {
        return dataSource.getSuppliers(params)
    }

    override suspend fun createSupplier(body: CreateSupplierParams): CreateSupplierResponse {
        return dataSource.createSupplier(body)
    }

    override suspend fun deleteSupplier(body: DeleteSupplierParams): DeleteSupplierResponse {
        return dataSource.deleteSupplier(body)
    }

    override suspend fun updateSupplier(id: String, body: UpdateSupplierParams): UpdateSupplierResponse {
        return dataSource.updateSupplier(id, body)
    }

}
