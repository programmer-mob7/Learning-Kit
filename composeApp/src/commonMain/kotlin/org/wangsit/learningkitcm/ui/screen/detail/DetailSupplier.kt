package org.wangsit.learningkitcm.ui.screen.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.wangsit.learningkitcm.data.model.Supplier
import org.wangsit.learningkitcm.data.source.network.model.response.SupplierItem
import org.wangsit.learningkitcm.ui.component.StatusBadge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailSupplier(
    supplier: SupplierItem,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatusBadge(
                status = supplier.status,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            DetailRow(label = "Company Name", value = supplier.companyName)
            DetailRow(label = "PIC", value = supplier.id)
            DetailRow(label = "PIC", value = supplier.picName)
            DetailRow(label = "PIC Phone", value = supplier.picName)
            DetailRow(label = "Country", value = supplier.country)
            DetailRow(label = "State", value = supplier.state)
        }
    }
}



@Composable
fun DetailRow(
    label: String,
    symbol: String = ":",
    value: String?
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier.width(100.dp)
        )
        Text(
            text = ":",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier.width(10.dp)
        )
        Text(
            text = value?.ifBlank { "-" } ?: "-",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )
    }
}