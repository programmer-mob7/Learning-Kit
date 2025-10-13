package org.wangsit.learningkitcm.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardActions // <-- Tambahkan import ini
import androidx.compose.foundation.text.KeyboardOptions // <-- Tambahkan import ini
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction // <-- Tambahkan import ini
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarTopBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchTriggered: (String) -> Unit,
    onClearClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    focusedTextColor: Color = Color.White
) {
    Column(
        modifier = modifier
            .widthIn(max = 200.dp)
    ) {
        UnderlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            placeholderText = "Search",
            leadingIcon = {
                // PERBAIKAN #1: Ikon ini sekarang hanya sebagai hiasan, tanpa aksi klik.
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon", // Deskripsi yang lebih sesuai
                    tint = Color(0xFF047857)
                )
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = onClearClick) { // Nama IconButton sudah konsisten
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear search", // Deskripsi yang lebih sesuai
                            tint = Color.LightGray
                        )
                    }
                }
            },
            // PERBAIKAN #2: Menambahkan aksi keyboard untuk memicu pencarian.
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchTriggered(query)
                }
            ),
            focusedBorderColor = Color.LightGray,
            unfocusedBorderColor = Color.LightGray,
            cursorColor = Color.White,
            focusedTextColor = focusedTextColor
        )
    }
}
