package org.wangsit.learningkitcm.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.wangsit.learningkitcm.icon.ValkyrieIcons
import org.wangsit.learningkitcm.icon.download_icon
import org.wangsit.learningkitcm.icon.filter_icon
import org.wangsit.learningkitcm.icon.list_icon


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    onNavigateUp: () -> Unit,
    onSearchTriggered: (String) -> Unit,
    onFilterClick: () -> Unit,
    onDownloadClick: () -> Unit,
    onLogClick: () -> Unit,

    // === new: props untuk selection mode ===
    selectionCount: Int = 0,
    onDeleteSelected: (() -> Unit)? = null,
    onCancelSelection: (() -> Unit)? = null,
) {
    var isSearchActive by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    val inSelectionMode = selectionCount > 0

    MediumTopAppBar(
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color(0xFF047857)
        ),
        title = {
            Text(
                text = if (inSelectionMode) selectionCount.toString() else title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
        },
        navigationIcon = {
            when {
                inSelectionMode -> {
                    IconButton(onClick = { onCancelSelection?.invoke() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Cancel selection",
                            tint = Color.White
                        )
                    }
                }
                canNavigateBack -> {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            }
        },
        actions = {
            if (inSelectionMode) {
                // === actions saat selection mode ===
                IconButton(onClick = { onDeleteSelected?.invoke() }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete selected", tint = Color.White)
                }
                IconButton(onClick = { onCancelSelection?.invoke() }) {
                    Icon(Icons.Default.Close, contentDescription = "Clear selection", tint = Color.White)
                }
            } else {
                AnimatedVisibility(visible = isSearchActive, enter = fadeIn(), exit = fadeOut()) {
                    SearchBarTopBar(
                        query = searchQuery,
                        onQueryChange = { searchQuery = it },
                        onClearClick = {
                            searchQuery = ""
                            onSearchTriggered("")
                        },
                        onSearchTriggered = onSearchTriggered,
                        focusedTextColor = Color.White,
                        modifier = Modifier.weight(1f)
                    )
                }
                IconButton(onClick = {
                    isSearchActive = !isSearchActive
                    if (!isSearchActive) searchQuery = ""
                }) {
                    Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                }
                IconButton(onClick = onFilterClick) {
                    Icon(ValkyrieIcons.filter_icon, contentDescription = "Filter", tint = Color.White)
                }
                IconButton(onClick = onDownloadClick) {
                    Icon(ValkyrieIcons.download_icon, contentDescription = "Download", tint = Color.White)
                }
                IconButton(onClick = onLogClick) {
                    Icon(ValkyrieIcons.list_icon, contentDescription = "List", tint = Color.White)
                }
            }
        },
    )
}


@Preview(showBackground = true)
@Composable
private fun MainTopAppBarPreview() {
    MaterialTheme {
        MainTopAppBar(
            title = "Preview",
            canNavigateBack = true,
            onNavigateUp = {},
            //onSearchClick = {},
            onFilterClick = {},
            onDownloadClick = {},
            onLogClick = {},
            onSearchTriggered = { TODO() }
        )
    }
}
