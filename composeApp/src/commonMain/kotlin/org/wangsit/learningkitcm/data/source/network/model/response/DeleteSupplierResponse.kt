package org.wangsit.learningkitcm.data.source.network.model.response

import kotlinx.serialization.Serializable

@Serializable
data class DeleteSupplierResponse(
    val data: Int? = null,
    val message: String,
    val status: Int
)