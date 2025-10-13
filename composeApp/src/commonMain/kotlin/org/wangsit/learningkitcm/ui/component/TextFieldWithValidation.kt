package org.wangsit.learningkitcm.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TextFieldWithValidation(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    isError: Boolean,
    errorMessage: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    maxLines: Int = 1,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 6.dp)
) {
    Column {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder) },
            isError = isError,
            keyboardOptions = keyboardOptions,
            maxLines = maxLines,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedTextColor = Color.Black,
                focusedBorderColor = if (isError) Color.Red else Color(0xFF047857),
                unfocusedBorderColor = if (isError) Color.Red else Color.Gray,
                unfocusedPlaceholderColor = Color.Gray,
                errorBorderColor = Color.Red,
                focusedTextColor = Color.Black,
                cursorColor = Color.Black,
            ),
            modifier = modifier
        )
        if (isError) {
            Text(
                text = errorMessage,
                color = Color.Red,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 8.dp, top = 2.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun TextFieldWithValidationPreview() {
    MaterialTheme {
        Column {
            TextFieldWithValidation(value = "", onValueChange = {}, label = "Name", placeholder = "Enter name", isError = true, errorMessage = "error")
            TextFieldWithValidation(value = "", onValueChange = {}, label = "Email", placeholder = "Enter email", isError = true, errorMessage = "email error")
            TextFieldWithValidation(value = "", onValueChange = {}, label = "Phone", placeholder = "Enter phone", isError = false, errorMessage = "phone error")
        }
    }
}