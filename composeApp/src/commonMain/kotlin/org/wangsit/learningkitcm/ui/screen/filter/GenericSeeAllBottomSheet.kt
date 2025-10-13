package org.wangsit.learningkitcm.ui.screen.filter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SearchBar
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.wangsit.learningkitcm.ui.component.CustomCheckbox
import org.wangsit.learningkitcm.ui.component.UnderlinedSearchBar
import kotlin.collections.plus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericSeeAllBottomSheet(
    title: String,
    items: List<String>,
    selectedItems: Set<String>,
    onSelectionChange: (Set<String>) -> Unit,
    onDismiss: () -> Unit,
    onApply: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var internalSelected by remember { mutableStateOf(selectedItems) }
    var query by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = MaterialTheme.shapes.large,
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(1f)
            ){
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Reset",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

            }

            // Search bar
            UnderlinedSearchBar(
                query = query,
                onQueryChange = { query = it }
            )

            Spacer(Modifier.height(12.dp))

            val filteredItems = items.filter {
                it.contains(query, ignoreCase = true)
            }

            // Checkbox ALL
            val allChecked =
                filteredItems.isNotEmpty() && filteredItems.all { it in internalSelected }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("All", fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.weight(1f))
                CustomCheckbox(
                    checked = allChecked,
                    onCheckedChange = { checked ->
                        internalSelected = if (checked) {
                            internalSelected + filteredItems
                        } else {
                            internalSelected - filteredItems.toSet()
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            filteredItems.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(item, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.weight(1f))
                    CustomCheckbox(
                        checked = item in internalSelected,
                        onCheckedChange = { checked ->
                            internalSelected = if (checked) {
                                internalSelected + item
                            } else {
                                internalSelected - item
                            }
                        }
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = {
                    onSelectionChange(internalSelected)
                    scope.launch { sheetState.hide() }.invokeOnCompletion { onApply() }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF047857)),
                shape = MaterialTheme.shapes.small
            ) {
                Text("Apply", color = Color.White)
            }
        }
    }
}