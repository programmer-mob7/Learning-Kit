package org.wangsit.learningkitcm.ui.screen.filter.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.wangsit.learningkitcm.data.model.GroupItem
import org.wangsit.learningkitcm.ui.component.UnderlinedSearchBar

@Composable
fun TreeSelection(
    item: GroupItem,
    modifier: Modifier = Modifier,
    paddingStart: Int = 0
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = paddingStart.dp, top = 4.dp, bottom = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(4.dp)
        ) {
            if (item.children.isNotEmpty()) {
                Icon(
                    imageVector = if (expanded) Icons.Default.ArrowDropDown else Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color.Black
                )
                Spacer(Modifier.width(4.dp))
            }
            Text(text = item.name, style = MaterialTheme.typography.bodyMedium)
        }

        if (expanded) {
            item.children.forEach { child ->
                TreeSelection(child, paddingStart = paddingStart + 16)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupSelectionBottomSheet(
    groups: List<GroupItem>,
    onDismiss: () -> Unit,
    onApply: (String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    var searchQuery by remember { mutableStateOf("") }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Title
            Text(
                text = "Group",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Search field
            UnderlinedSearchBar(
                query = searchQuery,
                onQueryChange = { },
            )

            Spacer(Modifier.height(12.dp))

            // List
            LazyColumn(
                modifier = Modifier.weight(1f, fill = true)
            ) {
                val filteredGroups = if (searchQuery.isBlank()) {
                    groups
                } else {
                    groups.filter { it.name.contains(searchQuery, ignoreCase = true) }
                }

                items(filteredGroups) { item ->
                    TreeSelection(item = item)
                }
            }

            Spacer(Modifier.height(16.dp))

            // Apply button
            Button(
                onClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        onApply(searchQuery)
                        onDismiss()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF047857))
            ) {
                Text("Apply", color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun GroupSelectionBottomSheetPreview() {
    val dummyGroups = listOf(
        GroupItem("All"),
        GroupItem("Head Office A", children = listOf(
            GroupItem("1st Floor"),
            GroupItem("2nd Floor", children = listOf(
                GroupItem("Room 202")
            ))
        )),
        GroupItem("Head Office B"),
        GroupItem("Head Office C"),
    )

    MaterialTheme {
        GroupSelectionBottomSheet(
            groups = dummyGroups,
            onDismiss = {},
            onApply = { }
        )
    }
}
