package org.wangsit.learningkitcm.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.wangsit.learningkitcm.data.model.Supplier
import org.wangsit.learningkitcm.data.model.SupplierStatus
import org.wangsit.learningkitcm.data.source.network.model.response.SupplierItem

@Composable
fun SupplierItem(
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
                    status = supplier.status,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                IconButton(onClick = onMoreClick) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Options",
                        tint = Color.Gray
                    )
                }
            }

            Spacer(Modifier.height(4.dp))

            supplier.companyName?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black
                )
            }
            Spacer(Modifier.height(2.dp))
            supplier.state?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black.copy(alpha = 0.7f)
                )
            }

            Spacer(Modifier.height(6.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                OrderChip(supplier.country)
            }

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        0.dp,
                        0.dp,
                        35.dp,
                        0.dp
                    ),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically

            ) {
                supplier.updatedAt?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Start)
                }

                Spacer(modifier = Modifier.weight(1f))

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Contact",
                    tint = Color(0xFF047857)
                )
                Spacer(Modifier.width(4.dp))
                supplier.picName?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF047857)
                    )
                }
            }
        }
    }
}

@Composable
fun OrderChip(orderNo: String?) {
    Box(
        modifier = Modifier
            .background(Color(0xFFE5E7EB), shape = RoundedCornerShape(50))
            .padding(horizontal = 10.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        if (orderNo != null) {
            Text(
                text = orderNo,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Black,
                modifier = Modifier.widthIn(max = 82.dp),
                overflow = TextOverflow.MiddleEllipsis,
                softWrap = false,
                maxLines = 1
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun SupplierItemPreview() {
//    MaterialTheme {
//        SupplierItem(
//            supplier = Supplier(
//                name = "Supplier Name",
//                location = "Supplier Location",
//                orderNo = "Order No",
//                date = "Date",
//                contactPerson = "Contact Person",
//                status = SupplierStatus.ACTIVE,
//                id = TODO(),
//                country = TODO(),
//                state = TODO(),
//                city = TODO(),
//                zip = TODO(),
//                address = TODO(),
//                companyPhone = TODO(),
//                picName = TODO(),
//                picPhone = TODO(),
//                picEmail = TODO()
//            ),
//            onMoreClick = {}
//        )
//    }
//}