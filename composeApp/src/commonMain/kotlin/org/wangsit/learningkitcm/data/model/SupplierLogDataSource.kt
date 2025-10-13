package org.wangsit.learningkitcm.data.model

import kotlinx.datetime.Clock


object SupplierLogDataSource {
    val logs = mutableListOf<SupplierLog>()
    private var nextLogId = 1L

    fun addLog(
        supplierId: String?,
        action: LogAction,
        description: String,
        user: String = "system",
        supplierName: String = "system"
    ) {
        logs.add(
            SupplierLog(
                logId = nextLogId++,
                action = action,
                description = description,
                user = user,
                timestamp = Clock.System.now(),
                supplierId = supplierId,
                supplierName = supplierName
            )
        )
    }

    fun getLogsForSupplier(supplierId: String): List<SupplierLog> { // Changed from Int to String
        return logs.filter { it.supplierId == supplierId }.sortedByDescending { it.timestamp }
    }

}