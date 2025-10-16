package org.wangsit.learningkitcm.data.source.network.dataSource

import io.github.aakira.napier.Napier
// Hapus import yang salah, kita tidak membutuhkannya di sini karena pemanggilan akan melalui interface 'SupplierApi'
// import io.ktor.client.utils.EmptyContent.contentType
// import io.ktor.http.ContentType
import org.wangsit.learningkitcm.data.source.network.model.request.CreateSupplierParams
import org.wangsit.learningkitcm.data.source.network.model.request.DeleteSupplierParams
import org.wangsit.learningkitcm.data.source.network.model.request.GetSuppliersParams
import org.wangsit.learningkitcm.data.source.network.model.request.UpdateSupplierParams
import org.wangsit.learningkitcm.data.source.network.model.response.CreateSupplierResponse
import org.wangsit.learningkitcm.data.source.network.model.response.DeleteSupplierResponse
import org.wangsit.learningkitcm.data.source.network.model.response.GetSuppliersResponse
import org.wangsit.learningkitcm.data.source.network.model.response.UpdateSupplierResponse
import org.wangsit.learningkitcm.data.source.network.services.SupplierApi

class SupplierApiDataSourceImpl(
    private val supplierApi: SupplierApi
) : SupplierApiDataSource {

    override suspend fun getSuppliers(params: GetSuppliersParams): GetSuppliersResponse {
        return supplierApi.getSuppliers(params)
    }

    override suspend fun createSupplier(body: CreateSupplierParams): CreateSupplierResponse {
        return supplierApi.createSupplier(body)
    }

    override suspend fun deleteSupplier(body: DeleteSupplierParams): DeleteSupplierResponse {
        Napier.i("Delegating DELETE request with body: $body", tag = "SupplierApiDataSource")
        val response = supplierApi.deleteSupplier(body)
        Napier.d("Received DELETE response: $response", tag = "SupplierApiDataSource")

        return response
    }

    override suspend fun updateSupplier(id: String, body: UpdateSupplierParams): UpdateSupplierResponse {
        Napier.i("Delegating UPDATE for supplier ID=$id", tag = "SupplierApiDataSource")
        val response = supplierApi.updateSupplier(id, body)
        Napier.d("Received UPDATE response: $response", tag = "SupplierApiDataSource")
        return response
    }

}
