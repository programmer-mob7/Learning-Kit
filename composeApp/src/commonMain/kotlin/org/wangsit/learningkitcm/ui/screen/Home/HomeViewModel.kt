package org.wangsit.learningkitcm.ui.screen.home

import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.wangsit.learningkitcm.data.repository.SupplierRepository
import org.wangsit.learningkitcm.data.source.network.model.request.DeleteSupplierParams
import org.wangsit.learningkitcm.data.source.network.model.request.GetSuppliersParams
import org.wangsit.learningkitcm.data.source.network.model.request.UpdateSupplierParams
import org.wangsit.learningkitcm.data.source.network.model.response.SupplierItem

data class HomeUiState(
    val suppliers: List<SupplierItem> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null
)

class HomeViewModel(
    private val supplierRepository: SupplierRepository
) : KoinComponent {
    private val viewModelScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _selectedIds = MutableStateFlow<Set<String>>(emptySet())
    val selectedIds: StateFlow<Set<String>> = _selectedIds.asStateFlow()

    init { getSuppliers() }

    fun getSuppliers() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            try {
                val params = GetSuppliersParams(limit = 20, page = 1)
                val response = supplierRepository.getSuppliers(params)
                _uiState.update {
                    it.copy(isLoading = false, suppliers = response.data?.data ?: emptyList())
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = e.message ?: "Terjadi kesalahan saat mengambil data supplier")
                }
            }
        }
    }

    fun setUpdatedSuppliers(list: List<SupplierItem>) {
        _uiState.update { it.copy(suppliers = list) }
    }

    fun searchSuppliers(keyword: String) {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            try {
                val params = GetSuppliersParams(limit = 20, page = 1, search = keyword.ifBlank { null })
                val response = supplierRepository.getSuppliers(params)
                _uiState.update {
                    it.copy(isLoading = false, suppliers = response.data?.data ?: emptyList())
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = e.message ?: "Gagal mencari supplier")
                }
            }
        }
    }

    fun toggleSelection(id: String?) {
        if (id == null) return
        _selectedIds.update { cur -> if (cur.contains(id)) cur - id else cur + id }
    }

    fun clearSelection() { _selectedIds.value = emptySet() }

    fun deleteSupplierSingle(id: String) {
        deleteSuppliers(listOf(id))
    }

    fun deleteSuppliersBulk() {
        val ids = _selectedIds.value.toList()
        if (ids.isEmpty()) return
        deleteSuppliers(ids)
    }

    private fun deleteSuppliers(ids: List<String>) {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            try {
                Napier.i("DELETE Request: ${ids.joinToString()}", tag = "SupplierVM")

                val response = supplierRepository.deleteSupplier(DeleteSupplierParams(supplierID = ids))
                Napier.d("DELETE Response: status=${response.status}, message=${response.message}", tag = "SupplierVM")

                if (response.status == 200 || response.status == 1) {
                    Napier.i("DELETE Success for ${ids.size} supplier(s)", tag = "SupplierVM")
                    getSuppliers()
                    clearSelection()
                } else {
                    Napier.w("DELETE API failed: ${response.message}", tag = "SupplierVM")
                    _uiState.update { it.copy(isLoading = false, errorMessage = response.message) }
                }
            } catch (e: Exception) {
                Napier.e("🔥 DELETE Exception: ${e.message}", e, tag = "SupplierVM")
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = e.message ?: "Gagal menghapus supplier")
                }
            }
        }
    }

    fun updateSupplier(id: String, params: UpdateSupplierParams) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                Napier.i("🟡 Sending update for supplier ID=$id", tag = "SupplierVM")

                val response = supplierRepository.updateSupplier(id, params)

                if (response.status == 200 || response.status == 1) {
                    Napier.i("UPDATE success, refreshing list", tag = "SupplierVM")
                    getSuppliers()
                } else {
                    Napier.w("UPDATE failed: ${response.message}", tag = "SupplierVM")
                    _uiState.update { it.copy(isLoading = false, errorMessage = response.message) }
                }

            } catch (e: Exception) {
                Napier.e("UPDATE exception: ${e.message}", e, tag = "SupplierVM")
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = "Gagal memperbarui supplier: ${e.message}")
                }
            }
        }
    }


}
