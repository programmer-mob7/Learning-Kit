package org.wangsit.learningkitcm.data.source.network.model.request

import kotlinx.serialization.Serializable

@Serializable
data class DeleteSupplierParams(
    val supplierID : List<String>
)