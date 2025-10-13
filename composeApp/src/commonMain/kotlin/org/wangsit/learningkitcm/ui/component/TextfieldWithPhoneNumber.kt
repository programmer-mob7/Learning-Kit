package org.wangsit.learningkitcm.ui.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType

import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.wangsit.learningkitcm.data.model.PhoneCodeDataSource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhoneNumberFieldDropdown(
    prefix: String,
    phone: String,
    onPhoneChange: (String) -> Unit,
    onPrefixChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.width(100.dp)

        ) {
            OutlinedTextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                value = prefix,
                onValueChange = {},
                readOnly = true,
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                },
                textStyle = LocalTextStyle.current.copy(color = Color.Black),
                shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFF047857),
                    unfocusedBorderColor = Color.Gray,
                    disabledBorderColor = Color.Gray
                )
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                PhoneCodeDataSource.phoneCodes.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item.code) },
                        onClick = {
                            onPrefixChange(item.code)
                            expanded = false
                        }
                    )
                }
            }
        }

        // Phone number input
        OutlinedTextField(
            value = phone,
            onValueChange = onPhoneChange,
            placeholder = { Text("Enter company phone number") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.weight(1f),
            textStyle = LocalTextStyle.current.copy(color = Color.Black),
            shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF047857),
                unfocusedBorderColor = Color.Gray
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PhoneNumberFieldDropdownPreview(){
    PhoneNumberFieldDropdown(
        prefix = "+62",
        phone = "08123456789",
        onPhoneChange = {},
        onPrefixChange = {}
    )
}
