package org.wangsit.learningkitcm.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.wangsit.learningkitcm.data.model.ToastType

@Composable
fun SnackBarConfirmation(
    toast: ToastType?,
    onDismiss: () -> Unit
) {
    if (toast != null) {
        val bgColor: Color
        val textColor = Color.White
        val icon: ImageVector
        val iconTint: Color

        when (toast) {
            is ToastType.Success -> {
                bgColor = Color(0xFF00A455)
                icon = Icons.Default.CheckCircle
                iconTint = Color.White
            }

            is ToastType.Error -> {
                bgColor = Color(0xFFD32F2F)
                icon = Icons.Default.Clear
                iconTint = Color.White
            }
        }

        LaunchedEffect(toast) {
            delay(2000)
            onDismiss()
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(bgColor)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(icon, contentDescription = null, tint = iconTint)
                Spacer(Modifier.width(8.dp))
                Text(
                    text = when (toast) {
                        is ToastType.Success -> toast.message
                        is ToastType.Error -> toast.message
                    },
                    color = textColor,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}


@Preview
@Composable
private fun SnackBarConfirmationPreview() {
    MaterialTheme {
        SnackBarConfirmation(
            toast = ToastType.Success("Success"),
            onDismiss = {}
        )
    }
}