package org.wangsit.learningkitcm.ui.screen.Home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.wangsit.learningkitcm.data.source.network.model.response.SupplierItem
import org.wangsit.learningkitcm.ui.component.OrderChip
import org.wangsit.learningkitcm.ui.component.StatusBadge

@Composable
fun SupplierList(
    suppliers: List<SupplierItem>,
    onShowMessage: (String, String) -> Unit,
    navController: NavHostController
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedSupplier by remember { mutableStateOf<SupplierItem?>(null) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(suppliers) { supplier ->
            SupplierCard(
                supplier = supplier,
                onMoreClick = {
                    selectedSupplier = supplier
                    showBottomSheet = true
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
            onShowMessage = onShowMessage,
        )
    }
}

@Composable
fun SupplierCard(
    supplier: SupplierItem,
    onMoreClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, Color(0xFFDAD9E3))
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                StatusBadge(
                    status = supplier.status ?: true,
                    modifier = Modifier.padding(end = 8.dp)
                )
//                Text(
//                    text = if (supplier.status == true) "Active" else "Inactive",
//                    color = if (supplier.status == true) Color(0xFF047857) else Color.Red,
//                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
//                )

                IconButton(onClick = onMoreClick) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Options",
                        tint = Color.Gray
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            Text(
                text = supplier.companyName ?: "Nama Perusahaan Tidak Tersedia",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            )
            Spacer(Modifier.height(2.dp))

            Text(
                text = "${supplier.state ?: ""}, ${supplier.country ?: "Lokasi Tidak Tersedia"}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(Modifier.height(6.dp))

            supplier.id?.firstOrNull()?.let {
                OrderChip( supplier.id)
            }

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = supplier.updatedAt?.take(10) ?: "",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "PIC",
                    tint = Color(0xFF047857)
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    text = supplier.picName ?: "-",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF047857)
                )
            }
        }
    }
}
