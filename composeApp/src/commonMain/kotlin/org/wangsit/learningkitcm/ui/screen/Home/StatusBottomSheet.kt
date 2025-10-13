package org.wangsit.learningkitcm.ui.screen.Home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.wangsit.learningkitcm.data.model.LogAction
import org.wangsit.learningkitcm.data.model.SupplierAction
import org.wangsit.learningkitcm.data.model.SupplierLocalDataSource
import org.wangsit.learningkitcm.data.model.SupplierLogDataSource
import org.wangsit.learningkitcm.data.model.SupplierStatus
import org.wangsit.learningkitcm.data.model.ToastType
import org.wangsit.learningkitcm.data.source.network.model.response.SupplierItem
import org.wangsit.learningkitcm.ui.component.ConfirmDialog
import org.wangsit.learningkitcm.ui.screen.detail.DetailSupplier
import org.wangsit.learningkitcm.ui.screen.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusBottomSheet(
    show: Boolean,
    onDismiss: () -> Unit,
    supplier: SupplierItem,
    navController: NavHostController,
    onShowMessage: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = org.koin.compose.koinInject() // ✅ inject viewModel KMP
) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedAction by remember { mutableStateOf<SupplierAction?>(null) }
    var toast by remember { mutableStateOf<ToastType?>(null) }
    var showDetail by remember { mutableStateOf(false) }

    if (show) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            containerColor = Color.White,
            dragHandle = { BottomSheetDefaults.DragHandle() }
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Spacer(modifier = Modifier.height(8.dp))

                TextButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        showDetail = true
                    }) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = Color.Blue
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Detail", color = Color.Black)
                    }
                }

                HorizontalDivider(thickness = 1.dp, color = Color(0xFFE0E0E0))

                TextButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        navController.navigate("supplier_edit/${supplier.id}")
                    }
                ){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = Color.DarkGray
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Edit", color = Color.Black)
                    }
                }

                HorizontalDivider(thickness = 1.dp, color = Color(0xFFE0E0E0))

                TextButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        selectedAction = SupplierAction.ACTIVATE
                        showDialog = true
                        //onDismiss()
                    }) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            Icons.Default.Check,
                            contentDescription = null,
                            tint = Color(0xFF00A455)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Activate",
                            color = Color.Black
                        )
                    }
                }
                HorizontalDivider(thickness = 1.dp, color = Color(0xFFE0E0E0))

                TextButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        selectedAction = SupplierAction.INACTIVATE
                        showDialog = true
                        //onDismiss()
                    }) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = null,
                            tint = Color(0xFF00A455)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Inactivate", color = Color.Black)

                    }
                }

                HorizontalDivider(thickness = 1.dp, color = Color(0xFFE0E0E0))

                TextButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    onClick = {
                        selectedAction = SupplierAction.DELETE
                        showDialog = true
                        //onDismiss()
                    }) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Delete", color = Color.Red)
                    }
                }
            }
        }
    }

    if (showDetail) {
        DetailSupplier(
            supplier = supplier,
            onDismiss = { showDetail = false }
        )
    }

    if (showDialog && selectedAction != null) {
        when (selectedAction) {
            SupplierAction.ACTIVATE -> {
                ConfirmDialog(
                    title = "Activate Supplier",
                    message = "${supplier.companyName} will be activated. Are you sure?",
                    confirmText = "Activate",
                    icon = Icons.Default.Check,
                    iconTint = Color(0xFF00A455),
                    onConfirm = {
                        val currentList = viewModel.uiState.value.suppliers.toMutableList()
                        val index = currentList.indexOfFirst { it.id == supplier.id }
                        if (index != -1) {
                            currentList[index] = currentList[index].copy(status = true)
                            viewModel.setUpdatedSuppliers(currentList)
                            onShowMessage("${supplier.companyName} activated", "success")
                        }
                        showDialog = false
                        onDismiss()
                    },
                    onDismiss = { showDialog = false }
                )
            }

            SupplierAction.INACTIVATE -> {
                ConfirmDialog(
                    title = "Inactivate Supplier",
                    message = "${supplier.companyName} will be inactivated. Are you sure?",
                    confirmText = "Inactivate",
                    icon = Icons.Default.Clear,
                    iconTint = Color(0xFF00A455),
                    onConfirm = {
                        val currentList = viewModel.uiState.value.suppliers.toMutableList()
                        val index = currentList.indexOfFirst { it.id == supplier.id }
                        if (index != -1) {
                            currentList[index] = currentList[index].copy(status = false)
                            viewModel.setUpdatedSuppliers(currentList)
                            onShowMessage("${supplier.companyName} Inactivated", "success")
                        }
                        onShowMessage("${supplier.companyName} Inactivated", "success")
                        showDialog = false
                        onDismiss()
                    },
                    onDismiss = { showDialog = false }
                )
            }

            SupplierAction.DELETE -> {}
//            SupplierAction.DELETE -> {
//                ConfirmDialog(
//                    title = "Delete Supplier",
//                    message = "${supplier.companyName} will be deleted. Are you sure?",
//                    confirmText = "Delete",
//                    icon = Icons.Default.Delete,
//                    iconTint = Color.Red,
//                    onConfirm = {
//                        SupplierLocalDataSource.suppliers.removeAt(supplier.id) // Cara aman untuk remove
//
//                        // TAMBAHKAN LOG DI SINI
//                        SupplierLogDataSource.addLog(
//                            supplierId = supplier.id,
//                            action = LogAction.DELETED,
//                            description = "Supplier '${supplier.companyName}' was deleted from the system."
//                        )
//
//                        onShowMessage("${supplier.companyName} Deleted", "error")
//                        showDialog = false
//                        onDismiss()
//                    },
//                    onDismiss = { showDialog = false }
//                )
//            }
            null -> Unit
        }
    }
}