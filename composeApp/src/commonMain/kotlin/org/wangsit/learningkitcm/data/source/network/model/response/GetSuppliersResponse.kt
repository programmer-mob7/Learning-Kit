package org.wangsit.learningkitcm.data.source.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetSuppliersResponse(
    val data: SupplierDataWrapper?,
    val message: String?,
    val status: Int?
)

@Serializable
data class SupplierDataWrapper(
    val totalRecords: Int?,
    val data: List<SupplierItem>?
)

@Serializable
data class SupplierItem(
    @SerialName("_id")
    val id: String?,
    val status: Boolean?,
    val companyName: String?,
    val country: String?,
    val state: String?,
    val item: List<ItemDetail>?,
    val picName: String?,

    @SerialName("updated_at")
    val updatedAt: String?
)

@Serializable
data class ItemDetail(
    val itemName: String?,
    val sku: List<String>?
)
