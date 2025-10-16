package org.wangsit.learningkitcm.ui.screen.Home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.wangsit.learningkitcm.data.source.network.model.response.SupplierItem
import org.wangsit.learningkitcm.ui.component.OrderChip
import org.wangsit.learningkitcm.ui.component.StatusBadge

@Composable
fun SupplierList(
    suppliers: List<SupplierItem>,
    onShowMessage: (String, String) -> Unit,
    navController: NavHostController,

    // 🔌 new:
    selectedIds: Set<String>,
    onToggleSelect: (String?) -> Unit,
    onDeleteSingle: (String) -> Unit
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedSupplier by remember { mutableStateOf<SupplierItem?>(null) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(suppliers) { supplier ->
            val isSelected = supplier.id != null && selectedIds.contains(supplier.id)

            SupplierCard(
                supplier = supplier,
                isSelected = isSelected,
                onMoreClick = {
                    // jika belum selection mode, munculkan bottom sheet
                    if (selectedIds.isEmpty()) {
                        selectedSupplier = supplier
                        showBottomSheet = true
                    } else {
                        // kalau sudah selection mode, tombol More bertindak sebagai toggle pilih
                        onToggleSelect(supplier.id)
                    }
                },
                onLongPress = { onToggleSelect(supplier.id) },
                onClick = {
                    if (selectedIds.isNotEmpty()) onToggleSelect(supplier.id)
                    // else: bisa diarahkan ke detail, saat ini dibiarkan noop
                }
            )
        }
    }

    if (showBottomSheet && selectedSupplier != null) {
        StatusBottomSheet(
            show = showBottomSheet,
            supplier = selectedSupplier!!,
            onDismiss = { showBottomSheet = false },
            navController = navController,
            onShowMessage = onShowMessage
        )
    }
}

@Composable
fun SupplierCard(
    supplier: SupplierItem,
    isSelected: Boolean,
    onMoreClick: () -> Unit,
    onLongPress: () -> Unit,
    onClick: () -> Unit
) {
    val borderColor = if (isSelected) Color(0xFF047857) else Color(0xFFDAD9E3)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(onClick = onClick, onLongClick = onLongPress),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(if (isSelected) 2.dp else 1.dp, borderColor)
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                StatusBadge(status = supplier.status ?: true, modifier = Modifier.padding(end = 8.dp))
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = onMoreClick) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Options", tint = Color.Gray)
                }
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = supplier.companyName ?: "Nama Perusahaan Tidak Tersedia",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = "${supplier.state.orEmpty()}, ${supplier.country ?: "Lokasi Tidak Tersedia"}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Spacer(Modifier.height(6.dp))

            supplier.id?.firstOrNull()?.let { _ ->
                OrderChip(supplier.id)
            }

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = supplier.updatedAt?.take(10) ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(imageVector = Icons.Default.Person, contentDescription = "PIC", tint = Color(0xFF047857))
                Spacer(Modifier.width(4.dp))
                Text(text = supplier.picName ?: "-", style = MaterialTheme.typography.bodyMedium, color = Color(0xFF047857))
            }
        }
    }
}
