package org.wangsit.learningkitcm.data.model

import androidx.compose.runtime.mutableStateListOf

data class Supplier(
    val id: Int,
    val name: String,
    val country: String? = "",
    val state: String? = "",
    val city: String? = "",
    val zip: String? = "",
    val address: String? = "",
    val companyPhone: String? = "",
    val picName: String? = "",
    val picPhone: String? = "",
    val picEmail: String? = "",
    val location: String,
    val orderNo: String,
    val date: String?,
    var status: SupplierStatus,
    val contactPerson: String?
)

object SupplierLocalDataSource {
    val suppliers = mutableStateListOf(
        Supplier(
            id = 1,
            name = "PT. ABC Indonesia",
            location = "Jakarta Utara, Indonesia",
            orderNo = "473112312132123151651615611515121354412",
            date = "Fri 29 Sept 2023 13:00:01",
            status = SupplierStatus.ACTIVE,
            contactPerson = "Nakamoto Y",
            picPhone = "+6281234567890",
            companyPhone = "+628123"
        ),
    )
}