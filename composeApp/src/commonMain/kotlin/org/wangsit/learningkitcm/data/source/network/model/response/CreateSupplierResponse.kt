package org.wangsit.learningkitcm.data.source.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class CreateSupplierResponse(
    val data: SupplierData?,
    val message: String,
    val status: Int
)

@Serializable
data class SupplierData(
    val _id: String,
    val status: Boolean,
    val companyName: String,
    val country: String,
    val state: String,
    val city: String? = null,
    val companyLocation: String,
    val companyPhoneNumber: String,
    val item: List<ItemResponse>,
    val zipCode: String,
    val picName: String,
    val picPhoneNumber: Long,
    val picEmail: String,
    val modifiedBy: String,
    val createdAt: String,
    val updatedAt: String
)

@Serializable
data class ItemResponse(
    val itemName: String,
    val sku: List<String>
)
