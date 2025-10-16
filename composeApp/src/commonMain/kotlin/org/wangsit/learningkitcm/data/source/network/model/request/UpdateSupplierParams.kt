package org.wangsit.learningkitcm.data.source.network.model.request

import kotlinx.serialization.Serializable


@Serializable
data class UpdateSupplierParams(
    val companyName: String,
    val item: List<ItemData>,
    val country: String,
    val state: String,
    val city: String,
    val zipCode: String,
    val companyLocation: String,
    val companyPhoneNumber: String,
    val picName: String,
    val picPhoneNumber: String,
    val picEmail: String
) {

    @Serializable
    data class ItemData(
        val itemName: String,
        val sku: List<String>
    )
}