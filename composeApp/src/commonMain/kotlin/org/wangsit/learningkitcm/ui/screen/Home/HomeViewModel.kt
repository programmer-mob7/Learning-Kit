package org.wangsit.learningkitcm.ui.screen.home

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
import org.wangsit.learningkitcm.data.source.network.model.request.GetSuppliersParams
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

    init {
        getSuppliers()
    }

    fun getSuppliers() {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            try {
                val params = GetSuppliersParams(limit = 20, page = 1)
                val response = supplierRepository.getSuppliers(params)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        suppliers = response.data?.data ?: emptyList()
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Terjadi kesalahan saat mengambil data supplier"
                    )
                }
            }
        }
    }

    fun setUpdatedSuppliers(list: List<SupplierItem>) {
        _uiState.update { it.copy(suppliers = list) }
    }

    fun updateSupplierStatusLocally(id: String?, newStatus: Boolean) {
        if (id == null) return
        val updatedList = _uiState.value.suppliers.toMutableList()
        val index = updatedList.indexOfFirst { it.id == id }

        if (index != -1) {
            updatedList[index] = updatedList[index].copy(status = newStatus)
            _uiState.update { it.copy(suppliers = updatedList) }
        }
    }

    fun searchSuppliers(keyword: String) {
        println("HomeViewModel: Memulai pencarian dengan keyword: '$keyword'")
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            try {
                val params = GetSuppliersParams(
                    limit = 20,
                    page = 1,
                    search = keyword.ifBlank { null }
                )

                println("HomeViewModel: Mengirim request pencarian ke server dengan params: $params")
                val response = supplierRepository.getSuppliers(params)

                println("HomeViewModel: Pencarian berhasil. Jumlah data ditemukan: ${response.data?.data?.size ?: 0}")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        suppliers = response.data?.data ?: emptyList()
                    )
                }
            } catch (e: Exception) {
                println("!!! HomeViewModel: Gagal melakukan pencarian. Error: ${e.message}")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "Gagal mencari supplier"
                    )
                }
            }
        }
    }
}

