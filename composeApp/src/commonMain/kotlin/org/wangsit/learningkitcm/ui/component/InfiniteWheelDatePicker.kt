package org.wangsit.learningkitcm.ui.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.isEmpty
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.wangsit.learningkitcm.icon.ValkyrieIcons
import org.wangsit.learningkitcm.icon.calendar_icon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicker() {
    var showDatePicker by remember { mutableStateOf(false) }
    var confirmedDate by remember { mutableStateOf<LocalDate?>(null) }
    var tempDate by remember { mutableStateOf<LocalDate?>(null) }
    val focusManager = LocalFocusManager.current

    TextField(
        value = confirmedDate?.toFormattedString() ?: "",
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
        // ... (colors tidak berubah)
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color(0xFF047857),
            unfocusedIndicatorColor = Color.Gray,
            disabledIndicatorColor = Color.LightGray,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent
        )
    )

    if (showDatePicker) {
        ModalBottomSheet(
            onDismissRequest = { showDatePicker = false },
            containerColor = Color.White,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Select Date", style = MaterialTheme.typography.titleMedium)
                Spacer(Modifier.height(12.dp))

                InfiniteWheelDatePicker(
                    initialDate = confirmedDate,
                    onDateSelected = { date -> tempDate = date }
                )

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = {
                        confirmedDate = tempDate
                        showDatePicker = false
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF047857))
                ) {
                    Text("Done", color = Color.White)
                }
            }
        }
    }
}

// Helper untuk format tanggal
private fun LocalDate.toFormattedString(): String {
    val day = this.dayOfMonth.toString().padStart(2, '0')
    val month = this.monthNumber.toString().padStart(2, '0')
    val monthName = this.month.name.lowercase().replaceFirstChar { it.titlecase() }
    val year = this.year
    return "$day $monthName $year"
}

@Composable
fun InfiniteWheelDatePicker(
    modifier: Modifier = Modifier,
    initialDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {
    val startDate =
        initialDate ?: Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    var currentDate by remember { mutableStateOf(startDate) }

    val months = remember {
        Month.values().map { it.name.lowercase().replaceFirstChar { c -> c.titlecase() } }
    }
    val years = remember { (1900..2100).map { it.toString() } }

    val daysInMonth = remember(currentDate.year, currentDate.monthNumber) {
        val first = LocalDate(currentDate.year, currentDate.monthNumber, 1)
        val next = first.plus(DatePeriod(months = 1))
        val count = first.daysUntil(next)
        (1..count).map { it.toString() }
    }

    LaunchedEffect(currentDate) {
        onDateSelected(currentDate)
    }

    Row(
        modifier = modifier.fillMaxWidth().height(200.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedContent(
            targetState = daysInMonth,
            label = "Animate Days Wheel",
            transitionSpec = {
                fadeIn() togetherWith fadeOut()
            }
        ) { currentDayList ->
            // === Wheel Tanggal ===
            InfiniteWheel(
                items = currentDayList,
                initialIndex = currentDate.dayOfMonth - 1,
                onItemSelected = { dayString ->
                    val newDay = dayString.toInt()

                    if (newDay != currentDate.dayOfMonth) {
                        currentDate = LocalDate(currentDate.year, currentDate.month, newDay)
                    }
                }
            )
        }

// === Wheel Bulan ===
        InfiniteWheel(
            items = months,
            initialIndex = currentDate.monthNumber - 1,
            onItemSelected = { monthString ->
                val newMonthNumber = months.indexOf(monthString) + 1
                // PERBAIKAN: Hanya update jika benar-benar berubah
                if (newMonthNumber != currentDate.monthNumber) {
                    val newDay =
                        safeDayOfMonth(currentDate.year, newMonthNumber, currentDate.dayOfMonth)
                    currentDate = LocalDate(currentDate.year, newMonthNumber, newDay)
                }
            }
        )

// === Wheel Tahun ===
        InfiniteWheel(
            items = years,
            initialIndex = currentDate.year - 1900,
            onItemSelected = { yearString ->
                val newYear = yearString.toInt()
                // PERBAIKAN: Hanya update jika benar-benar berubah
                if (newYear != currentDate.year) {
                    val newDay =
                        safeDayOfMonth(newYear, currentDate.monthNumber, currentDate.dayOfMonth)
                    currentDate = LocalDate(newYear, currentDate.monthNumber, newDay)
                }
            }
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun InfiniteWheel(
    items: List<String>,
    initialIndex: Int,
    onItemSelected: (String) -> Unit
) {
    if (items.isEmpty()) return

    val itemHeight = 40.dp
    val visibleItemsCount = 5
    val totalHeight = itemHeight * visibleItemsCount
    val verticalPadding = (totalHeight - itemHeight) / 2

    val listState = rememberLazyListState()

    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    val centerIndex by remember {
        derivedStateOf {
            val layoutInfo = listState.layoutInfo
            if (layoutInfo.visibleItemsInfo.isEmpty()) 0
            else {
                // Kalkulasi tetap sama, mencari yang paling dekat dengan pusat viewport
                val viewportCenterY =
                    (layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset) / 2
                layoutInfo.visibleItemsInfo.minByOrNull { kotlin.math.abs((it.offset + it.size / 2) - viewportCenterY) }?.index
                    ?: 0
            }
        }
    }

    // Scroll ke posisi awal hanya sekali saat komponen pertama kali dibuat
    LaunchedEffect(listState, items) {
        val targetIndex = if (initialIndex in items.indices) initialIndex else 0
        val scrollPosition =
            (Short.MAX_VALUE / 2) - ((Short.MAX_VALUE / 2) % items.size) + targetIndex
        listState.scrollToItem(scrollPosition)
    }

    Box(
        modifier = Modifier
            .width(115.dp)
            .height(totalHeight) // Tinggi Box sesuai dengan total tinggi item
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(itemHeight) // Tinggi highlight box sama dengan tinggi item
        ) {
            HorizontalDivider(
                modifier = Modifier.align(Alignment.TopCenter),
                thickness = 1.dp,
                color = Color(0xFF047857)
            )
            HorizontalDivider(
                modifier = Modifier.align(Alignment.BottomCenter),
                thickness = 1.dp,
                color = Color(0xFF047857)
            )
        }

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = CenterHorizontally,
            // PERBAIKAN 4: Tambahkan padding vertikal
            contentPadding = PaddingValues(vertical = verticalPadding),
            // PERBAIKAN 5: Terapkan FlingBehavior
            flingBehavior = flingBehavior
        ) {
            items(Short.MAX_VALUE.toInt()) { index ->
                if (items.isNotEmpty()) {
                    val itemIndex = index % items.size
                    Text(
                        text = items[itemIndex],
                        fontSize = if (index == centerIndex) 20.sp else 16.sp,
                        textAlign = TextAlign.Center,
                        // PERBAIKAN 6: Atur tinggi setiap item agar konsisten
                        modifier = Modifier
                            .height(itemHeight)
                            .wrapContentHeight(), // Membuat teks di tengah vertikal item
                        color = when {
                            index == centerIndex -> Color(0xFF047857)
                            else -> Color.Gray.copy(alpha = 0.6f)
                        }
                    )
                }
            }
        }
    }

    // Callback saat scroll berhenti, logika ini sudah benar (SEBENARNYA KURANG TEPAT)
    LaunchedEffect(listState.isScrollInProgress, items) {
        if (!listState.isScrollInProgress && items.isNotEmpty()) {
            val selectedItemIndex = centerIndex % items.size
            if (selectedItemIndex in items.indices) {
                onItemSelected(items[selectedItemIndex])
            }
        }
    }


}


private fun daysInMonth(year: Int, month: Int): Int {
    val first = LocalDate(year, month, 1)
    val next = first.plus(DatePeriod(months = 1))
    return first.daysUntil(next)
}

fun safeDayOfMonth(year: Int, month: Int, day: Int): Int {
    return day.coerceAtMost(daysInMonth(year, month))
}

@Preview(showBackground = true)
@Composable
fun DatePickerPreview() {
    MaterialTheme {
        InfiniteWheelDatePicker(
            initialDate = LocalDate(2023, 1, 1),
            onDateSelected = {}
        )
    }
}
