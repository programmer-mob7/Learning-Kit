package org.wangsit.learningkitcm.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.wangsit.learningkitcm.data.model.SupplierStatus

@Composable
fun StatusBadge(status: Boolean?, modifier: Modifier) {
    val (text, bgColor, textColor) = when (status) {
        true -> Triple("Active", Color(0xFFDFF5E1), Color(0xFF00A455))
        false -> Triple("Inactive", Color(0xFFFFE0E0), Color(0xFFD32F2F))
        null -> TODO()
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp))
            .background(bgColor)
            .padding(horizontal = 8 .dp, vertical = 4.dp)
    ) {
        Text(text = text, color = textColor, style = MaterialTheme.typography.bodySmall)
    }
}