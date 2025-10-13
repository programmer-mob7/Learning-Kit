//package org.wangsit.learningkitcm.ui.screen.logSupplier
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.navigationBarsPadding
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.statusBarsPadding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import androidx.navigation.compose.rememberNavController
//import kotlinx.datetime.Clock
//import org.jetbrains.compose.ui.tooling.preview.Preview
//import org.wangsit.learningkitcm.data.model.LogAction
//import org.wangsit.learningkitcm.data.model.SupplierLog
//import org.wangsit.learningkitcm.data.model.SupplierLogDataSource
//import org.wangsit.learningkitcm.ui.component.MainTopAppBar
//
//@Composable
//fun LogSupplier(
//    navController: NavController, // 1. Terima NavController untuk navigasi
//    modifier: Modifier = Modifier
//) {
//    // 2. Ambil SEMUA log dari data source dan urutkan dari yang terbaru
//    val allLogs = SupplierLogDataSource.logs.sortedByDescending { it.timestamp }
//
//    Scaffold(
//        topBar = {
//            MainTopAppBar(
//                title = "Change Log: Supplier",
//                canNavigateBack = true,
//                onNavigateUp = { navController.popBackStack() }, // Aksi kembali
//                // Aksi lain tidak melakukan apa-apa
//                onSearchClick = {},
//                onFilterClick = {},
//                onDownloadClick = {},
//                onLogClick = {}
//            )
//        },
//
//        modifier = modifier
//            .statusBarsPadding()
//            .navigationBarsPadding(),
//    ) { innerPadding ->
//        LazyColumn(
//            modifier = Modifier.padding(innerPadding),
//            contentPadding = PaddingValues(16.dp),
//            verticalArrangement = Arrangement.spacedBy(12.dp)
//        ) {
//            items(allLogs) { log ->
//                LogItem(log = log)
//            }
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun LogSupplierPreview() {
//    // Buat data dummy untuk pratinjau
//    SupplierLogDataSource.logs.clear()
//    SupplierLogDataSource.logs.addAll(
//        listOf(
//            SupplierLog(1, 1, "PT. ABC Indonesia", LogAction.DELETED, "Company Name", "John D", Clock.System.now()),
//            SupplierLog(2, 2, "PT. Sinar Mas Dunia", LogAction.EDITED, "PIC Name\nMark Lee → Nakamoto Yuta", "John D", Clock.System.now()),
//            SupplierLog(3, 3, "PT ABC", LogAction.CREATED, "Company Name", "John D", Clock.System.now())
//        )
//    )
//
//    MaterialTheme {
//        LogSupplier(
//            // 5. Berikan NavController dummy untuk pratinjau
//            navController = rememberNavController()
//        )
//    }
//}