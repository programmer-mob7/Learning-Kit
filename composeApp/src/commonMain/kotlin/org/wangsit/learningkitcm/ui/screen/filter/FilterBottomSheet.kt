package org.wangsit.learningkitcm.ui.screen.filter

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.wangsit.learningkitcm.data.model.FilterItem
import org.wangsit.learningkitcm.data.model.GroupItem
import org.wangsit.learningkitcm.data.model.SupplierStatus
import org.wangsit.learningkitcm.icon.ValkyrieIcons
import org.wangsit.learningkitcm.icon.add_icon
import org.wangsit.learningkitcm.ui.component.DatePicker
import org.wangsit.learningkitcm.ui.component.DatePickerField
import org.wangsit.learningkitcm.ui.component.InfiniteWheelDatePicker
import org.wangsit.learningkitcm.ui.screen.filter.component.GroupSelectionBottomSheet
import org.wangsit.learningkitcm.ui.screen.filter.component.TreeSelection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    onDismiss: () -> Unit,
    onApply: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    var seeAllCategory by remember { mutableStateOf<String?>(null) }
    var showTree by remember { mutableStateOf(false) }

    var selectedStatus by remember { mutableStateOf(setOf<String>()) }
    var selectedSuppliers by remember { mutableStateOf(setOf<String>()) }
    var selectedCities by remember { mutableStateOf(setOf<String>()) }
    var selectedItems by remember { mutableStateOf(setOf<String>()) }
    var selectedModifiedBy by remember { mutableStateOf(setOf<String>()) }
    var selectedGroup by remember { mutableStateOf<GroupItem?>(null) }


    val filterMap = mapOf(
        "Status" to FilterItem.StatusFilter.map { it.name },
        "Supplier" to FilterItem.SupplierFilter.map { it.name },
        "City" to FilterItem.CityFilter.map { it.name },
        "Item Name" to FilterItem.ItemFilter.map { it.name },
        "Modified By" to FilterItem.ModifiedByFilter.map { it.name }
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        containerColor = Color.White
    ) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp).fillMaxHeight(0.6f)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().weight(1f, fill = false)
                    .verticalScroll(scrollState)
            ) {
                Text(
                    "Filter",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(Modifier.height(16.dp))

                FilterCategory(
                    title = "Active",
                    items = filterMap["Status"] ?: emptyList(),
                    selectedItems = selectedStatus,
                    onSelectionChange = { selectedStatus = it },
                    onSeeAllClick = { seeAllCategory = "Status" }
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    "Group",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                Spacer(Modifier.height(16.dp))

                OutlinedButton(
                    onClick = { showTree = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, Color(0xFF047857)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp)
                ) {
                     Text(
                            text = selectedGroup?.name ?: "Item Group",
                            textAlign = TextAlign.Center,
                            )
//                        Icon(
//                            imageVector = ValkyrieIcons.add_icon,
//                            contentDescription = "Select Item Group"
//                        )
                }

                Spacer(Modifier.height(12.dp))

                FilterCategory(
                    title = "Supplier",
                    items = filterMap["Supplier"] ?: emptyList(),
                    selectedItems = selectedSuppliers,
                    onSelectionChange = { selectedSuppliers = it },
                    onSeeAllClick = { seeAllCategory = "Supplier" }
                )

                Spacer(Modifier.height(12.dp))

                FilterCategory(
                    title = "City",
                    items = filterMap["City"] ?: emptyList(),
                    selectedItems = selectedCities,
                    onSelectionChange = { selectedCities = it },
                    onSeeAllClick = { seeAllCategory = "City" }
                )

                Spacer(Modifier.height(12.dp))

                FilterCategory(
                    title = "Item Name",
                    items = filterMap["Item Name"] ?: emptyList(),
                    selectedItems = selectedItems,
                    onSelectionChange = { selectedItems = it },
                    onSeeAllClick = { seeAllCategory = "Item Name" }
                )

                Spacer(Modifier.height(12.dp))

                FilterCategory(
                    title = "Modified By",
                    items = filterMap["Modified By"] ?: emptyList(),
                    selectedItems = selectedModifiedBy,
                    onSelectionChange = { selectedModifiedBy = it },
                    onSeeAllClick = { seeAllCategory = "Modified By" }
                )

                Spacer(Modifier.height(12.dp))

                DatePicker()
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = {  },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF047857))
            ) { Text("Apply", color = Color.White) }
        }
    }

    seeAllCategory?.let { category ->
        val items = when (category) {
            "Supplier" -> FilterItem.SupplierFilter.map { it.name }
            else -> filterMap[category] ?: emptyList()
        }

        GenericSeeAllBottomSheet(
            title = category,
            items = items,
            selectedItems = when (category) {
                "Supplier" -> selectedSuppliers
                "City" -> selectedCities
                "Item Name" -> selectedItems
                "Modified By" -> selectedModifiedBy
                else -> emptySet()
            },
            onSelectionChange = {
                when (category) {
                    "Supplier" -> selectedSuppliers = it
                    "City" -> selectedCities = it
                    "Item Name" -> selectedItems = it
                    "Modified By" -> selectedModifiedBy = it
                }
            },
            onDismiss = { seeAllCategory = null },
            onApply = { seeAllCategory = null }
        )
    }

    if (showTree) {
        GroupSelectionBottomSheet(
            groups = GroupItem.Groups,
            onDismiss = { showTree = false },
            onApply = { groupName ->
                //selectedGroup = findGroupByName(GroupItem.Groups, groupName)
                showTree = false
            }
        )
    }
}


@Composable
fun FilterCategory(
    title: String,
    items: List<String>,
    selectedItems: Set<String>,
    onSelectionChange: (Set<String>) -> Unit,
    maxVisible: Int = 5,
    onSeeAllClick: (() -> Unit)? = null
) {
    val visibleItems = if (items.size > maxVisible) items.take(maxVisible) else items
    val remainingCount = (items.size - maxVisible).coerceAtLeast(0)

    Column {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
            if (onSeeAllClick != null && items.size > maxVisible) {
                Text(
                    "See all",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                    color = Color(0xFF047857),
                    modifier = Modifier.clickable { onSeeAllClick() }
                )
            }
        }
        Spacer(Modifier.height(8.dp))
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            visibleItems.forEach { item ->
                val isSelected = item in selectedItems

                OutlinedButton(
                    onClick = {
                        val newSet = if (isSelected) selectedItems - item else selectedItems + item
                        onSelectionChange(newSet)
                    },
                    border = BorderStroke(1.dp, Color(0xFF047857)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = if (isSelected) Color(0xFF047857) else Color.White,
                        contentColor = if (isSelected) Color.White else Color(0xFF047857)
                    ),
                    shape = RoundedCornerShape(50)
                ) {
                    Icon(
                        imageVector = ValkyrieIcons.add_icon,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(item) }
            }

            if (remainingCount > 0 && onSeeAllClick != null) {
                OutlinedButton(
                    onClick = onSeeAllClick,
                    border = BorderStroke(1.dp, Color.Gray),
                    colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White, contentColor = Color(0xFF047857)),
                    shape = RoundedCornerShape(50)
                ) { Text("+$remainingCount") }
            }
        }
    }
}