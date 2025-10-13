package org.wangsit.learningkitcm.ui.screen.createSupplier

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.wangsit.learningkitcm.data.repository.SupplierRepository
import org.wangsit.learningkitcm.data.source.network.dataSource.SupplierApiDataSource
import org.wangsit.learningkitcm.data.source.network.model.request.CreateSupplierParams

class CreateEditViewModel(
    private val supplierRepository: SupplierRepository
) : ViewModel() {

    private val _createState = MutableStateFlow<CreateEditState>(CreateEditState.Idle)
    val createState = _createState.asStateFlow()

    fun createSupplier(supplierData: CreateSupplierParams) {
        viewModelScope.launch {
            _createState.value = CreateEditState.Loading
            try {
                val response = supplierRepository.createSupplier(supplierData)

                // Sukses kalau status 2xx atau gunakan flag success dari server bila ada
                if (response.status in 200..299) {
                    _createState.value = CreateEditState.Success(response.message)
                } else {
                    _createState.value = CreateEditState.Error(response.message)
                }
            } catch (e: Exception) {
                _createState.value = CreateEditState.Error(e.message ?: "Tidak dapat terhubung ke server")
            }
        }
    }

    fun resetState() {
        _createState.value = CreateEditState.Idle
    }
}
