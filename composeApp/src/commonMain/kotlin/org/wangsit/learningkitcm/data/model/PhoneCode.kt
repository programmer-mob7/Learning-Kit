package org.wangsit.learningkitcm.data.model

data class PhoneCode(val code: String)

object PhoneCodeDataSource {
    val phoneCodes = listOf(
        PhoneCode("+62"),
        PhoneCode("+60"),
        PhoneCode("+65"),
        PhoneCode("+66")
    )
}