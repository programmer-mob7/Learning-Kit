package org.wangsit.learningkitcm.data.model

sealed class ToastType {
    data class Success(val message: String) : ToastType()
    data class Error(val message: String) : ToastType()
}