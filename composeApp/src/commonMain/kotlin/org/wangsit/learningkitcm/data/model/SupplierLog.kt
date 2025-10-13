package org.wangsit.learningkitcm.data.model

// In: data/model/SupplierLog.kt

import kotlinx.datetime.Instant

enum class LogAction {
    CREATED, EDITED, DELETED, ACTIVATED, INACTIVATED
}

data class SupplierLog(
    val logId: Long,
    val supplierId: String?,
    val supplierName: String,
    val action: LogAction,
    val description: String,
    val user: String,
    val timestamp: Instant
)

