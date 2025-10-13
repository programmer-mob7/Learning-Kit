package org.wangsit.learningkitcm.ui.screen.createSupplier

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import org.wangsit.learningkitcm.data.model.CityDataSource
import org.wangsit.learningkitcm.data.model.CountryDataSource
import org.wangsit.learningkitcm.data.model.StateDataSource
import org.wangsit.learningkitcm.data.model.Supplier
import org.wangsit.learningkitcm.data.source.network.model.request.CreateSupplierParams
import org.wangsit.learningkitcm.data.source.network.model.request.Item
import org.wangsit.learningkitcm.ui.component.CustomCheckbox
import org.wangsit.learningkitcm.ui.component.GenericBottomSheet
import org.wangsit.learningkitcm.ui.component.PhoneNumberFieldDropdown
import org.wangsit.learningkitcm.ui.component.TextFieldWithValidation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEditSupplier(
    navController: NavHostController? = null,
    supplier: Supplier? = null,
    title: String = if (supplier == null) "Add Supplier" else "Edit Supplier",
    modifier: Modifier = Modifier,
    screenTitle: String = title,
    supplierId: String? = null,
    viewModel: CreateEditViewModel = koinInject()
) {
    val createState by viewModel.createState.collectAsState()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Form states
    var name by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var zip by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var companyPhone by remember { mutableStateOf("") }
    var picName by remember { mutableStateOf("") }
    var picPhone by remember { mutableStateOf("") }
    var picEmail by remember { mutableStateOf("") }

    var stayOnForm by remember { mutableStateOf(false) }
    var phonePrefixCompany by remember { mutableStateOf("+62") }
    var phonePrefixPIC by remember { mutableStateOf("+62") }

    val fullPhoneCompany = "$phonePrefixCompany$companyPhone"
    val fullPhonePIC = "$phonePrefixPIC$picPhone"

    var showCountryBottomSheet by remember { mutableStateOf(false) }
    var showStateBottomSheet by remember { mutableStateOf(false) }
    var showCityBottomSheet by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    // Handle state changes from ViewModel
    LaunchedEffect(createState) {
        when (val s = createState) {
            is CreateEditState.Success -> {
                snackbarHostState.showSnackbar(s.message)
                if (!stayOnForm) {
                    navController?.popBackStack()
                } else {
                    resetForm(
                        onReset = {
                            name = ""; country = ""; state = ""; city = ""; zip = ""
                            address = ""; companyPhone = ""; picName = ""; picPhone = ""; picEmail = ""
                        }
                    )
                    viewModel.resetState()
                }
            }
            is CreateEditState.Error -> {
                snackbarHostState.showSnackbar(s.message)
                viewModel.resetState()
            }
            else -> Unit
        }
    }

    Scaffold(
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = { Text(screenTitle, fontSize = 16.sp, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController?.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF047857),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Stay on this form after submitting")
                    Spacer(Modifier.weight(1f))
                    CustomCheckbox(checked = stayOnForm, onCheckedChange = { stayOnForm = it })
                }
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = {
                        showError = true

                        val isValid = name.isNotBlank()
                                && country.isNotBlank()
                                && state.isNotBlank()
                                && city.isNotBlank()
                                && zip.isNotBlank()
                                && address.isNotBlank()
                                && companyPhone.isNotBlank()
                                && picName.isNotBlank()
                                && picPhone.isNotBlank()
                                && isValidEmail(picEmail)

                        if (!isValid) {
                            scope.launch {
                                snackbarHostState.showSnackbar("Form tidak valid, periksa kembali inputan.")
                            }
                            return@Button
                        }

                        val items = listOf(Item(itemName = "Default Item", sku = listOf("SKU-DEFAULT")))

                        val body = CreateSupplierParams(
                            companyName = name,
                            item = items,
                            country = country,
                            state = state,
                            city = city,
                            zipCode = zip,
                            companyLocation = address,
                            companyPhoneNumber = fullPhoneCompany,
                            picName = picName,
                            picPhoneNumber = fullPhonePIC,
                            picEmail = picEmail
                        )

                        viewModel.createSupplier(body)
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(5.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF047857))
                ) {
                    val btnText = when (createState) {
                        CreateEditState.Loading -> "Submitting..."
                        else -> if (supplierId == null) "Submit" else "Save"
                    }
                    Text(btnText, color = Color.White)
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            // === INPUT FIELD SECTIONS ===
            Text("Company Name *", fontSize = 12.sp, color = Color.Black)
            TextFieldWithValidation(
                value = name,
                onValueChange = { name = it },
                label = "Company Name",
                isError = showError && name.isBlank(),
                placeholder = "Enter company name",
                errorMessage = "Company name must not be empty",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Spacer(Modifier.height(12.dp))

            // Country
            Text("Country", fontSize = 12.sp)
            DropdownField(
                value = country,
                onClick = { showCountryBottomSheet = true },
                placeholder = "Select country"
            )
            Spacer(Modifier.height(12.dp))

            // State
            Text("State", fontSize = 12.sp)
            DropdownField(
                value = state,
                onClick = { showStateBottomSheet = true },
                placeholder = "Select state"
            )
            Spacer(Modifier.height(12.dp))

            // City
            Text("City", fontSize = 12.sp)
            DropdownField(
                value = city,
                onClick = { showCityBottomSheet = true },
                placeholder = "Select city"
            )
            Spacer(Modifier.height(12.dp))

            // ZIP
            Text("ZIP Code", fontSize = 12.sp)
            TextFieldWithValidation(
                value = zip,
                onValueChange = { zip = it },
                label = "ZIP Code",
                isError = showError && (zip.isBlank() || zip.length > 15),
                errorMessage = "Max 15 Characters",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                placeholder = "ZIP Code"
            )
            Spacer(Modifier.height(12.dp))

            // Address
            Text("Company Address", fontSize = 12.sp)
            TextFieldWithValidation(
                value = address,
                onValueChange = { address = it },
                label = "Company Address",
                isError = showError && (address.isBlank() || address.length > 120),
                errorMessage = "Max 120 Characters",
                modifier = Modifier.fillMaxWidth(),
                placeholder = "Company Address",
                maxLines = 2,
                keyboardOptions = KeyboardOptions.Default
            )
            Spacer(Modifier.height(12.dp))

            // Company Phone
            Text("Company Phone Number", fontSize = 12.sp)
            PhoneNumberFieldDropdown(
                prefix = phonePrefixCompany,
                phone = companyPhone,
                onPhoneChange = { companyPhone = it },
                onPrefixChange = { phonePrefixCompany = it }
            )
            Spacer(Modifier.height(12.dp))

            // PIC Name
            Text("PIC Name", fontSize = 12.sp)
            TextFieldWithValidation(
                value = picName,
                onValueChange = { picName = it },
                label = "PIC Name",
                isError = showError && (picName.isBlank() || picName.length > 30),
                errorMessage = "Max 30 Characters",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier.fillMaxWidth(),
                placeholder = "PIC Name"
            )
            Spacer(Modifier.height(12.dp))

            // PIC Phone
            Text("PIC Phone Number", fontSize = 12.sp)
            PhoneNumberFieldDropdown(
                prefix = phonePrefixPIC,
                phone = picPhone,
                onPhoneChange = { picPhone = it },
                onPrefixChange = { phonePrefixPIC = it }
            )
            Spacer(Modifier.height(12.dp))

            // PIC Email
            Text("PIC Email", fontSize = 12.sp)
            TextFieldWithValidation(
                value = picEmail,
                onValueChange = { picEmail = it },
                label = "PIC Email",
                placeholder = "PIC Email",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = showError && !isValidEmail(picEmail),
                errorMessage = "Invalid email address",
            )
            Spacer(Modifier.height(12.dp))
        }
    }

    // === BottomSheets ===
    if (showCountryBottomSheet) {
        GenericBottomSheet(
            title = "Select Country",
            items = CountryDataSource.countries,
            selected = country,
            onDismiss = { showCountryBottomSheet = false },
            onApply = { selected ->
                country = selected
                showCountryBottomSheet = false
            }
        )
    }

    if (showStateBottomSheet) {
        GenericBottomSheet(
            title = "Select State",
            items = StateDataSource.states,
            selected = state,
            onDismiss = { showStateBottomSheet = false },
            onApply = { selected ->
                state = selected
                showStateBottomSheet = false
            }
        )
    }

    if (showCityBottomSheet) {
        GenericBottomSheet(
            title = "Select City",
            items = CityDataSource.cities,
            selected = city,
            onDismiss = { showCityBottomSheet = false },
            onApply = { selected ->
                city = selected
                showCityBottomSheet = false
            }
        )
    }
}

// Helper: reusable dropdown field UI
@Composable
private fun DropdownField(
    value: String,
    onClick: () -> Unit,
    placeholder: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            enabled = false,
            readOnly = true,
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text(placeholder, color = Color.Gray) },
            trailingIcon = {
                Icon(Icons.Default.ArrowDropDown, contentDescription = placeholder, tint = Color(0xFF047857))
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF047857),
                unfocusedBorderColor = Color.Gray,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                disabledBorderColor = Color.Gray,
                disabledTextColor = Color.Black,
                cursorColor = Color.Black
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

private fun resetForm(onReset: () -> Unit) = onReset()

private fun isValidEmail(email: String): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
    return email.matches(emailRegex)
}