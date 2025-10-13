package org.wangsit.learningkitcm.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SupplierNavigation(
    modifier: Modifier = Modifier,
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    0.dp,
                    0.dp,
                    10.dp,
                    10.dp
                )
            )
            .background(Color(0xFF047857))
            .padding(bottom = 12.dp, top = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        SupplierToggleBar(
            selectedTab = selectedTab,
            onTabSelected = onTabSelected,
            modifier = Modifier.width(350.dp)
        )
    }
}

@Composable
fun SupplierToggleBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val tabs = listOf("list", "activities")
    val titles = listOf("List", "Supplier Activities")

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        tabs.forEachIndexed { index, tabIdentifier ->
            val isSelected = selectedTab == tabIdentifier
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp)) // Koreksi dari 16 ke 16.dp jika memang ukuran dp
                    .background(if (isSelected) Color(0xFF047857) else Color.White) // Warna dari tema
                    .clickable { onTabSelected(tabIdentifier) } // Panggil callback dengan identifier tab
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = titles[index],
                    color = if (isSelected) Color.White else Color(0xFF00A455), // Warna dari tema
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NavigationSupplierPreview() {
    MaterialTheme {
        var previewSelectedTab by remember { mutableStateOf("list") }
        SupplierNavigation(
            selectedTab = previewSelectedTab,
            onTabSelected = { tab -> previewSelectedTab = tab }
        )
    }
}