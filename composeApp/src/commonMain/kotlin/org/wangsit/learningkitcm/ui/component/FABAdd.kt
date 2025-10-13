package org.wangsit.learningkitcm.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FABMain(onClick: () -> Unit) {
    FloatingActionButton(
        shape = MaterialTheme.shapes.extraLarge,
        onClick = onClick,
        containerColor = Color(0xFF047857),
        contentColor = Color.White,
    ) {
        Icon(Icons.Default.Add, contentDescription = "Floating Button Add")
    }
}

@Preview
@Composable
private fun FABMainPreview() {
    MaterialTheme {
        FABMain(onClick = {})
    }
}