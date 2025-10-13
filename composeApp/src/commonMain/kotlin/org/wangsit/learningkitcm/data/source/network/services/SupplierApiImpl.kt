package org.wangsit.learningkitcm.data.source.network.services

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.wangsit.learningkitcm.data.source.network.ApiConstants
import org.wangsit.learningkitcm.data.source.network.ApiConstants.TOKEN
import org.wangsit.learningkitcm.data.source.network.model.request.CreateSupplierParams
import org.wangsit.learningkitcm.data.source.network.model.request.GetSuppliersParams
import org.wangsit.learningkitcm.data.source.network.model.response.CreateSupplierResponse
import org.wangsit.learningkitcm.data.source.network.model.response.GetSuppliersResponse

class SupplierApiImpl(private val client: HttpClient) : SupplierApi {

    override suspend fun getSuppliers(params: GetSuppliersParams): GetSuppliersResponse {
        return client.get("${ApiConstants.BASE_URL}/supplier") {
            headers{
                append(HttpHeaders.Authorization, "Bearer $TOKEN")
                append(HttpHeaders.Accept, "application/json")
            }
            url {
                params.search?.let { parameters.append("search", it) }
                params.supplier?.forEach { parameters.append("supplier", it) }
                params.city?.forEach { parameters.append("city", it) }
                params.itemName?.forEach { parameters.append("itemName", it) }
                params.modifiedBy?.let { parameters.append("modifiedBy", it) }
                params.page?.let { parameters.append("page", it.toString()) }
                params.limit?.let { parameters.append("limit", it.toString()) }
                params.sortOrder?.let { parameters.append("sortOrder", it.toString()) }
                params.sortBy?.let { parameters.append("sortBy", it) }
            }
        }.body()
    }

    override suspend fun createSupplier(body: CreateSupplierParams): CreateSupplierResponse {
        return client.post("${ApiConstants.BASE_URL}/supplier") {
            headers{
                append(HttpHeaders.Authorization, "Bearer $TOKEN")
                append(HttpHeaders.Accept, "application/json")
            }
            contentType(ContentType.Application.Json)
            setBody(body)
        }.body()
    }
}