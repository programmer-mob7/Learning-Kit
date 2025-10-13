package org.wangsit.learningkitcm.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.compose.koinInject
import org.wangsit.learningkitcm.data.model.ToastType
import org.wangsit.learningkitcm.ui.component.*
import org.wangsit.learningkitcm.ui.screen.Home.SupplierList
import org.wangsit.learningkitcm.ui.screen.createSupplier.CreateEditSupplier
import org.wangsit.learningkitcm.ui.screen.filter.FilterBottomSheet
import org.wangsit.learningkitcm.ui.screen.home.HomeViewModel
import org.wangsit.learningkitcm.ui.screen.supplierActivities.SupplierActivities

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.SupplierMain.route
    ) {
        composable(Screen.SupplierMain.route) {
            MainSupplierScreen(navController)
        }

        composable (Screen.SupplierCreate.route){
             CreateEditSupplier(
                 navController = navController,
                 title = "Add Supplier",
             )
        }

//        composable(Screen.SupplierEdit.route) {
//        }

//        composable(Screen.SupplierLog.route) {
//            LogSupplier(navController = navController)
//        }
    }
}

@Composable
fun MainSupplierScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinInject()
) {
    val uiState by viewModel.uiState.collectAsState()

    var selectedTab by remember { mutableStateOf("list") }
    var toast by remember { mutableStateOf<ToastType?>(null) }
    var showFilterSheet by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            MainTopAppBar(
                title = "Supplier",
                canNavigateBack = false,
                onNavigateUp = {},
                //onSearchClick = {},
                onFilterClick = { showFilterSheet = true },
                onDownloadClick = {},
                onLogClick = { navController.navigate(Screen.SupplierLog.route) },
                onSearchTriggered = { keyword ->
                    viewModel.searchSuppliers(keyword)
                }
            )
        },
        floatingActionButton = {
            FABMain(onClick = { navController.navigate(Screen.SupplierCreate.route) })
        },
        modifier = Modifier.statusBarsPadding().navigationBarsPadding()
    ) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {

            if (!uiState.isLoading && uiState.suppliers.isEmpty()) {
                Text(
                    text = "Supplier not found",
                    textAlign = TextAlign.Center
                )
            } else {
                SupplierList(
                    suppliers = uiState.suppliers,
                    onShowMessage = { _, _ ->  },
                    navController = navController
                    //isLoading = uiState.isLoading,
                    //modifier = Modifier.fillMaxSize()
                )
            }

            Column {
                SupplierNavigation(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )

                when {
                    // Kondisi 1: Loading
                    uiState.isLoading -> {
                        CircularProgressIndicator()
                    }

                    // Kondisi 2: Error
                    uiState.errorMessage != null -> {
                        Text(
                            text = "Error: ${uiState.errorMessage}",
                            color = Color.Red,
                            textAlign = TextAlign.Center
                        )
                    }

                    // Kondisi 3: Sukses, tapi datanya kosong (Ini yang Anda inginkan)
                    uiState.suppliers.isEmpty() -> {
                        if (selectedTab == "list") {
                            Text(
                                text = "Supplier not found",
                                textAlign = TextAlign.Center,
                            )
                        } else {
                            // Tampilkan pesan kosong untuk tab "Activities" jika perlu
                            SupplierActivities()
                        }
                    }

                    else -> when (selectedTab) {
                        "list" -> SupplierList(
                            suppliers = uiState.suppliers,
                            onShowMessage = { msg, type ->
                                toast = when (type) {
                                    "success" -> ToastType.Success(msg)
                                    "error" -> ToastType.Error(msg)
                                    else -> ToastType.Success(msg)
                                }
                            },
                            navController = navController
                        )
                        "activities" -> SupplierActivities()
                    }
                }
            }

            if (toast != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp)
                ) {
                    SnackBarConfirmation(
                        toast = toast,
                        onDismiss = { toast = null }
                    )
                }
            }
        }
    }

    if (showFilterSheet) {
        FilterBottomSheet(onDismiss = { showFilterSheet = false }, onApply = {})
    }
}
