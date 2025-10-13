package org.wangsit.learningkitcm.ui.screen.createSupplier

sealed class CreateEditState {
    object Idle : CreateEditState()
    object Loading : CreateEditState()
    data class Success(val message: String) : CreateEditState()
    data class Error(val message: String) : CreateEditState()
}
