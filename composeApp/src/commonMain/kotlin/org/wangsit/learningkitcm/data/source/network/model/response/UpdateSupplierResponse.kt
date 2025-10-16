package org.wangsit.learningkitcm.data.source.network.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateSupplierResponse(
    val status: Int,
    val message: String,
    val data: Data?
) {
    @Serializable
    data class Data(
        @SerialName("\$set")
        val set: SupplierItem?
    )
}