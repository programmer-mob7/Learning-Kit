package org.wangsit.learningkitcm.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
    onLogClick: () -> Unit
) {

    var isSearchActive by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    MediumTopAppBar(
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color(0xFF047857)
        ),
        title = {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = onNavigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }
        },
        actions = {
            AnimatedVisibility(
                visible = isSearchActive,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                SearchBarTopBar(
                    query = searchQuery,
                    onQueryChange = { newQuery ->
                        searchQuery = newQuery
                    },
                    onClearClick = {
                        searchQuery = ""
                        onSearchTriggered("")
                    },
                    onSearchTriggered = onSearchTriggered,
                    focusedTextColor = Color.White,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 3.dp)
                )
            }
            IconButton(onClick = {
                isSearchActive = !isSearchActive
                if (!isSearchActive) searchQuery = ""
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White
                )
            }

            IconButton(onClick = onFilterClick) {
                Icon(
                    imageVector = ValkyrieIcons.filter_icon,
                    contentDescription = "Filter",
                    tint = Color.White
                )
            }
            IconButton(onClick = onDownloadClick) {
                Icon(
                    imageVector = ValkyrieIcons.download_icon,
                    contentDescription = "Download",
                    tint = Color.White
                )
            }
       
            IconButton(onClick = onLogClick) {
                Icon(
                    imageVector = ValkyrieIcons.list_icon,
                    contentDescription = "List",
                    tint = Color.White
                )
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
