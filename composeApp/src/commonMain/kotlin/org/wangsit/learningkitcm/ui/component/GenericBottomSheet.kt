package org.wangsit.learningkitcm.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.wangsit.learningkitcm.data.model.SelectionItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericBottomSheet(
    title: String,
    items: List<SelectionItem>,
    selected: String?,
    onDismiss: () -> Unit,
    onApply: (String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var selectedItem by remember { mutableStateOf(selected) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if (title.isNotEmpty()) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            items.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { selectedItem = item.name },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = item.name,
                        modifier = Modifier.weight(1f),
                        color = if (selectedItem == item.name) Color(0xFF047857) else Color.Black
                    )
                    Checkbox(
                        checked = selectedItem == item.name,
                        onCheckedChange = { selectedItem = item.name },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color(0xFF047857),
                            checkmarkColor = Color.White,
                        )
                    )
                }
            }


            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    selectedItem?.let { onApply(it) }
                    scope.launch { sheetState.hide() }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF047857)),
                shape = RoundedCornerShape(5.dp)
            ) {
                Text("Apply", color = Color.White)
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
private fun GenericBottomSheetPreview() {
    MaterialTheme {
        var selectedItem by remember { mutableStateOf<String?>(null) }
        GenericBottomSheet(
            title = "Select Item",
            items = listOf(SelectionItem("tes")),
            selected = selectedItem,
            onDismiss = {},
            onApply = { selectedItem = it }
        )

    }
}