package org.wangsit.learningkitcm.data.source.network.model.request

import kotlinx.serialization.Serializable

@Serializable
data class GetSuppliersParams(
    val search: String? = null,
    val supplier: List<String>? = null,
    val city: List<String>? = null,
    val itemName: List<String>? = null,
    val modifiedBy: String? = null,
    val page: Int? = 1,
    val limit: Int? = 10,
    val sortOrder: Int? = 1,
    val sortBy: String? = null
)

