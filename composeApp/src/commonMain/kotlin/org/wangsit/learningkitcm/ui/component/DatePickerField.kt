package org.wangsit.learningkitcm.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import network.chaintech.kmp_date_time_picker.ui.datepicker.WheelDatePickerView
import network.chaintech.kmp_date_time_picker.utils.DateTimePickerView
import network.chaintech.kmp_date_time_picker.utils.WheelPickerDefaults
import org.wangsit.learningkitcm.icon.ValkyrieIcons
import org.wangsit.learningkitcm.icon.calendar_icon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField() {
    val focusManager = LocalFocusManager.current
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf("") }
    
    Text(
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = Bold,
        text = "Last Modified",
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {

        TextField(
            value = selectedDate,
            onValueChange = {},
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (focusState.isFocused) {
                        showDatePicker = true
                        focusManager.clearFocus(force = true)
                    }
                },
            placeholder = { Text("Select date") },
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(
                        imageVector = ValkyrieIcons.calendar_icon,
                        contentDescription = "Select date",
                        tint = Color(0xFF047857)
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFF047857),   // garis bawah saat fokus
                unfocusedIndicatorColor = Color.Gray,       // garis bawah saat tidak fokus
                disabledIndicatorColor = Color.LightGray,   // garis bawah saat disabled
                focusedContainerColor = Color.Transparent,  // background transparan saat fokus
                unfocusedContainerColor = Color.Transparent,// background transparan saat tidak fokus
                disabledContainerColor = Color.Transparent  // background transparan saat disabled
            )
        )

        if (showDatePicker) {
            WheelDatePickerView(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 22.dp, bottom = 26.dp),
                showDatePicker = showDatePicker,
                title = "DUE DATE",
                doneLabel = "Done",
                titleStyle = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF047857),
                ),
                doneLabelStyle = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF047857),
                ),
                dateTextColor = Color(0xFF047857),
                selectorProperties = WheelPickerDefaults.selectorProperties(
                    borderColor = Color.LightGray,
                ),
                rowCount = 5,
                height = 180.dp,
                dateTextStyle = TextStyle(
                    fontWeight = FontWeight(600),
                ),
                dragHandle = {
                    HorizontalDivider(
                        modifier = Modifier.padding(top = 8.dp).width(50.dp).clip(CircleShape),
                        thickness = 4.dp,
                        color = Color(0xFFE8E4E4)
                    )
                },
                shape = RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp),
                dateTimePickerView = DateTimePickerView.BOTTOM_SHEET_VIEW,
                onDoneClick = {
                    selectedDate = it.toString()
                    showDatePicker = false
                },
                onDismiss = {
                    showDatePicker = false
                },
            )
        }
    }
}