package org.wangsit.learningkitcm.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnderlinedSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearchClick: () -> Unit = {},
    onClearClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier){
        UnderlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            placeholderText = "Search",
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color(0xFF047857)
                )
            },
            trailingIcon = { // ⬅️ Tambahan ikon kanan
                if (query.isNotEmpty()) {
                    androidx.compose.material3.IconButton(onClick = onClearClick) {
                        Icon(
                            imageVector = Icons.Default.Search  ,
                            contentDescription = "Search action",
                            tint = Color.White
                        )
                    }
                }
            },
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.LightGray,
            cursorColor = Color.Black
        )
    }
}