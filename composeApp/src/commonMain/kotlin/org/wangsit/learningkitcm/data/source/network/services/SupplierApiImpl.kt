package org.wangsit.learningkitcm.data.source.network.services

import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.*
import org.wangsit.learningkitcm.data.source.network.ApiConstants
import org.wangsit.learningkitcm.data.source.network.ApiConstants.TOKEN
import org.wangsit.learningkitcm.data.source.network.model.request.CreateSupplierParams
import org.wangsit.learningkitcm.data.source.network.model.request.DeleteSupplierParams
import org.wangsit.learningkitcm.data.source.network.model.request.GetSuppliersParams
import org.wangsit.learningkitcm.data.source.network.model.request.UpdateSupplierParams
import org.wangsit.learningkitcm.data.source.network.model.response.CreateSupplierResponse
import org.wangsit.learningkitcm.data.source.network.model.response.DeleteSupplierResponse
import org.wangsit.learningkitcm.data.source.network.model.response.GetSuppliersResponse
import org.wangsit.learningkitcm.data.source.network.model.response.UpdateSupplierResponse

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

    override suspend fun deleteSupplier(body: DeleteSupplierParams): DeleteSupplierResponse {
        Napier.i("Preparing DELETE request: ${ApiConstants.BASE_URL}/supplier", tag = "SupplierApi")

        val response = client.delete("${ApiConstants.BASE_URL}/supplier") {
            headers {
                append(HttpHeaders.Authorization, "Bearer $TOKEN")
                append(HttpHeaders.Accept, "application/json")
            }
            contentType(ContentType.Application.Json)
            setBody(body)
        }

        Napier.d("DELETE response status: ${response.status}", tag = "SupplierApi")

        if (response.status.value == 401) {
            val text = response.bodyAsText()
            Napier.w("Unauthorized: $text", tag = "SupplierApi")
            throw Exception("Unauthorized. Check your token or login session.")
        }

        if (response.status.value !in 200..299) {
            val errorBody = response.bodyAsText()
            Napier.w("Delete failed. Code=${response.status.value}, Body=$errorBody", tag = "SupplierApi")
            throw Exception("Delete supplier failed: $errorBody")
        }

        val parsed = response.body<DeleteSupplierResponse>()
        Napier.i("Delete success: $parsed", tag = "SupplierApi")
        return parsed
    }

    override suspend fun updateSupplier(id: String, body: UpdateSupplierParams): UpdateSupplierResponse {
        Napier.i("➡️ Preparing UPDATE request for supplier ID=$id", tag = "SupplierApi")

        val response = client.put("${ApiConstants.BASE_URL}/supplier/$id") {
            headers {
                append(HttpHeaders.Authorization, "Bearer $TOKEN")
                append(HttpHeaders.Accept, "application/json")
            }
            contentType(ContentType.MultiPart.FormData) // sesuai header dari backend
            setBody(body)
        }

        Napier.d("📤 UPDATE response status: ${response.status}", tag = "SupplierApi")

        if (response.status.value !in 200..299) {
            val errorBody = response.bodyAsText()
            Napier.w("⚠️ UPDATE failed. Code=${response.status.value}, Body=$errorBody", tag = "SupplierApi")
            throw Exception("Update supplier failed: $errorBody")
        }

        val parsed = response.body<UpdateSupplierResponse>()
        Napier.i("✅ Update success: $parsed", tag = "SupplierApi")
        return parsed
    }


}