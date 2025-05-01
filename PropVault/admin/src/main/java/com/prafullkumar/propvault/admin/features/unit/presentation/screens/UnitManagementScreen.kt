package com.prafullkumar.propvault.admin.features.unit.presentation.screens


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafullkumar.common.models.DealStatus
import com.prafullkumar.common.models.PaymentStatus
import com.prafullkumar.propvault.admin.app.navigation.AdminRoutes
import com.prafullkumar.propvault.admin.features.unit.domain.UnitWithDealsAndPayments
import com.prafullkumar.propvault.admin.features.unit.presentation.viewmodels.UnitEditViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitManagementScreen(
    navController: NavController,
    unitId: String = "",
    developmentId: String,
    developmentName: String,
    buildingId: String,
    buildingName: String,
    viewModel: UnitEditViewModel,
    unitWithDetails: UnitWithDealsAndPayments
) {
    val isEditMode = unitId != ""
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditMode) "Edit Unit" else "Add New Unit") },
                navigationIcon = {
                    IconButton(onClick = {
                        if (isEditMode) {
                            navController.popBackStack(
                                AdminRoutes.UnitScreen(unitId),
                                inclusive = true
                            )
                            navController.navigate(AdminRoutes.UnitScreen(unitId))
                        } else {
                            navController.popBackStack(
                                AdminRoutes.Units(
                                    buildingId = buildingId,
                                    buildingName = buildingName,
                                    developmentId = developmentId,
                                    developmentName = developmentName
                                ),
                                inclusive = true
                            )
                            navController.navigate(
                                AdminRoutes.Units(
                                    buildingId = buildingId,
                                    buildingName = buildingName,
                                    developmentId = developmentId,
                                    developmentName = developmentName
                                )
                            )
                        }
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                })
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Location Info Card
            Card(
                modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Location Info",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Development: $developmentName")
                    Text("Building: $buildingName")
                }
            }

            // Unit Details Card
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Unit Details",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = viewModel.uiState.name,
                        onValueChange = {
                            viewModel.uiState = viewModel.uiState.copy(name = it)
                        },
                        label = { Text("Unit Name *") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = viewModel.uiState.name.isEmpty() && viewModel.uiState.showValidationError
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    UnitCreationDateField(viewModel)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = viewModel.uiState.unitCode,
                        onValueChange = {
                            viewModel.uiState = viewModel.uiState.copy(unitCode = it)
                        },
                        label = { Text("Unit Code *") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = viewModel.uiState.unitCode.isEmpty() && viewModel.uiState.showValidationError
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = viewModel.uiState.unitType,
                        onValueChange = {
                            viewModel.uiState = viewModel.uiState.copy(unitType = it)
                        },
                        label = { Text("Unit Type *") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = viewModel.uiState.unitType.isEmpty() && viewModel.uiState.showValidationError
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = viewModel.uiState.price,
                        onValueChange = {
                            viewModel.uiState = viewModel.uiState.copy(price = it)
                        },
                        label = { Text("Price *") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        isError = (viewModel.uiState.price.isEmpty() || viewModel.uiState.price.toDoubleOrNull() == null) && viewModel.uiState.showValidationError
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    var statusExpanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox(
                        expanded = statusExpanded, onExpandedChange = { statusExpanded = it }) {
                        OutlinedTextField(
                            value = viewModel.uiState.status,
                            onValueChange = { },
                            label = { Text("Status *") },
                            readOnly = true,
                            singleLine = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = statusExpanded) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            isError = viewModel.uiState.status.isEmpty() && viewModel.uiState.showValidationError
                        )
                        ExposedDropdownMenu(
                            expanded = statusExpanded,
                            onDismissRequest = { statusExpanded = false }) {
                            listOf("Available", "Reserved", "Sold").forEach { option ->
                                DropdownMenuItem(text = { Text(option) }, onClick = {
                                    viewModel.uiState = viewModel.uiState.copy(status = option)
                                    statusExpanded = false
                                })
                            }
                        }
                    }
                }
            }

            // Deal Section
            if (isEditMode) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (viewModel.uiState.existingDeal) "Edit Deal" else "Add Deal",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    if (!viewModel.uiState.existingDeal) {
                        Switch(
                            checked = viewModel.uiState.showDealSection, onCheckedChange = {
                                viewModel.uiState = viewModel.uiState.copy(showDealSection = it)
                            })
                    }
                }

                if (viewModel.uiState.showDealSection || viewModel.uiState.existingDeal) {
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            OutlinedTextField(
                                value = viewModel.uiState.customerName,
                                onValueChange = {
                                    viewModel.uiState = viewModel.uiState.copy(customerName = it)
                                },
                                label = { Text("Customer Name *") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                isError = viewModel.uiState.customerName.isEmpty() && viewModel.uiState.showValidationError
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = viewModel.uiState.customerPhone,
                                onValueChange = {
                                    viewModel.uiState = viewModel.uiState.copy(customerPhone = it)
                                },
                                label = { Text("Customer Phone *") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                isError = viewModel.uiState.customerPhone.isEmpty() && viewModel.uiState.showValidationError
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = viewModel.uiState.customerEmail,
                                onValueChange = {
                                    viewModel.uiState = viewModel.uiState.copy(customerEmail = it)
                                },
                                label = { Text("Customer Email") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = viewModel.uiState.dealTotalPrice,
                                onValueChange = {
                                    viewModel.uiState = viewModel.uiState.copy(dealTotalPrice = it)
                                },
                                label = { Text("Total Price *") },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                isError = (viewModel.uiState.dealTotalPrice.isEmpty() || viewModel.uiState.dealTotalPrice.toDoubleOrNull() == null) && viewModel.uiState.showValidationError
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = viewModel.uiState.assignedAgent,
                                onValueChange = {
                                    viewModel.uiState = viewModel.uiState.copy(assignedAgent = it)
                                },
                                label = { Text("Assigned Agent *") },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                isError = viewModel.uiState.assignedAgent.isEmpty() && viewModel.uiState.showValidationError
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                            DealDateField(viewModel)

                            Spacer(modifier = Modifier.height(8.dp))
                            ClosingDateField(viewModel)
                            Spacer(modifier = Modifier.height(8.dp))

                            var dealStatusExpanded by remember { mutableStateOf(false) }
                            ExposedDropdownMenuBox(
                                expanded = dealStatusExpanded,
                                onExpandedChange = { dealStatusExpanded = it }) {
                                OutlinedTextField(
                                    value = viewModel.uiState.dealStatus.name,
                                    onValueChange = { },
                                    readOnly = true,
                                    singleLine = true,
                                    label = { Text("Deal Status *") },
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = dealStatusExpanded
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .menuAnchor()
                                )

                                ExposedDropdownMenu(
                                    expanded = dealStatusExpanded,
                                    onDismissRequest = { dealStatusExpanded = false }) {
                                    DealStatus.entries.forEach { status ->
                                        DropdownMenuItem(text = { Text(status.name) }, onClick = {
                                            viewModel.uiState =
                                                viewModel.uiState.copy(dealStatus = status)
                                            dealStatusExpanded = false
                                        })
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = viewModel.uiState.dealRemarks,
                                onValueChange = {
                                    viewModel.uiState = viewModel.uiState.copy(dealRemarks = it)
                                },
                                label = { Text("Remarks") },
                                modifier = Modifier.fillMaxWidth()
                            )

                            // Payment Section
                            if (!viewModel.uiState.existingDeal || unitWithDetails.deals.firstOrNull()?.payments?.isEmpty() == true) {
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "Initial Payment *",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    value = viewModel.uiState.dueAmount,
                                    onValueChange = {
                                        viewModel.uiState = viewModel.uiState.copy(dueAmount = it)
                                    },
                                    label = { Text("Due Amount") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true
                                )

                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = viewModel.uiState.depositedAmount,
                                    onValueChange = {
                                        viewModel.uiState =
                                            viewModel.uiState.copy(depositedAmount = it)
                                    },
                                    label = { Text("Deposited Amount *") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    isError = (viewModel.uiState.depositedAmount.isEmpty() || viewModel.uiState.depositedAmount.toDoubleOrNull() == null) && viewModel.uiState.showValidationError
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                OutlinedTextField(
                                    value = viewModel.uiState.totalPaymentPrice,
                                    onValueChange = {
                                        viewModel.uiState =
                                            viewModel.uiState.copy(totalPaymentPrice = it)
                                    },
                                    label = { Text("Total Price*") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    isError = (viewModel.uiState.totalPaymentPrice.isEmpty() || viewModel.uiState.totalPaymentPrice.toDoubleOrNull() == null) && viewModel.uiState.showValidationError
                                )
                                OutlinedTextField(
                                    value = viewModel.uiState.amount,
                                    onValueChange = {
                                        viewModel.uiState = viewModel.uiState.copy(amount = it)
                                    },
                                    label = { Text("Amount*") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true,
                                    isError = (viewModel.uiState.amount.isEmpty() || viewModel.uiState.amount.toDoubleOrNull() == null) && viewModel.uiState.showValidationError
                                )

                                Spacer(modifier = Modifier.height(8.dp))
                                var paymentStatusExpanded by remember { mutableStateOf(false) }
                                ExposedDropdownMenuBox(
                                    expanded = paymentStatusExpanded,
                                    onExpandedChange = { paymentStatusExpanded = it }) {
                                    OutlinedTextField(
                                        value = viewModel.uiState.paymentStatus.name,
                                        onValueChange = { },
                                        readOnly = true,
                                        singleLine = true,
                                        label = { Text("Payment Status *") },
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(
                                                expanded = paymentStatusExpanded
                                            )
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .menuAnchor()
                                    )

                                    ExposedDropdownMenu(
                                        expanded = paymentStatusExpanded,
                                        onDismissRequest = { paymentStatusExpanded = false }) {
                                        PaymentStatus.entries.forEach { status ->
                                            DropdownMenuItem(
                                                text = { Text(status.name) },
                                                onClick = {
                                                    viewModel.uiState = viewModel.uiState.copy(
                                                        paymentStatus = status
                                                    )
                                                    paymentStatusExpanded = false
                                                })
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                PaymentDateField(viewModel)

                                Spacer(modifier = Modifier.height(8.dp))

                                OutlinedTextField(
                                    value = viewModel.uiState.paymentRemarks,
                                    onValueChange = {
                                        viewModel.uiState =
                                            viewModel.uiState.copy(paymentRemarks = it)
                                    },
                                    label = { Text("Payment Remarks") },
                                    modifier = Modifier.fillMaxWidth(),
                                    singleLine = true
                                )
                            }
                        }
                    }
                }
            }

            if (viewModel.uiState.showValidationError) {
                Text(
                    text = viewModel.uiState.validationMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Save Button
            Button(
                enabled = !viewModel.isSaving,
                onClick = {
                    viewModel.uiState = viewModel.uiState.copy(showValidationError = true)

                    // Validate common unit fields
                    if (viewModel.uiState.name.isEmpty() ||
                        viewModel.uiState.unitCode.isEmpty() ||
                        viewModel.uiState.unitType.isEmpty() ||
                        viewModel.uiState.price.isEmpty() ||
                        viewModel.uiState.price.toDoubleOrNull() == null ||
                        viewModel.uiState.status.isEmpty()
                    ) {
                        Toast.makeText(
                            context,
                            "Please fill all required unit fields",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    if ((viewModel.uiState.showDealSection || viewModel.uiState.existingDeal) &&
                        (viewModel.uiState.customerName.isEmpty() ||
                                viewModel.uiState.customerPhone.isEmpty() ||
                                viewModel.uiState.dealTotalPrice.isEmpty() ||
                                viewModel.uiState.dealTotalPrice.toDoubleOrNull() == null ||
                                viewModel.uiState.assignedAgent.isEmpty() ||
                                viewModel.uiState.dealDate.isEmpty() ||
                                // Initial payment validation
                                viewModel.uiState.depositedAmount.isEmpty() ||
                                viewModel.uiState.depositedAmount.toDoubleOrNull() == null ||
                                viewModel.uiState.totalPaymentPrice.isEmpty() ||
                                viewModel.uiState.totalPaymentPrice.toDoubleOrNull() == null ||
                                viewModel.uiState.paymentDate.isEmpty())
                    ) {
                        Toast.makeText(
                            context,
                            "Please fill all required deal and payment fields",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }
                    Log.d("Testing:", "Unit ID: $unitId")
                    viewModel.validateAndSave(
                        isEditMode = isEditMode,
                        unitId = unitId,
                        developmentId = developmentId,
                        developmentName = developmentName,
                        buildingId = buildingId,
                        buildingName = buildingName,
                        onSuccess = {
                            if (it) {
                                if (isEditMode) {
                                    navController.popBackStack(
                                        AdminRoutes.UnitScreen(unitId),
                                        inclusive = true
                                    )
                                    navController.navigate(AdminRoutes.UnitScreen(unitId))
                                } else {
                                    navController.popBackStack()
                                }
                            } else {
                                Toast.makeText(
                                    context, "Failed to save unit", Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text("Save")
            }

            // Show existing payments if any
            if (isEditMode) {
                val existingDeal = unitWithDetails.deals.firstOrNull()
                if (existingDeal != null && existingDeal.payments.isNotEmpty()) {
                    Text(
                        text = "Payment History",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    existingDeal.payments.forEach { payment ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text("Amount: $${payment.amount}")
                                    Text("Status: ${payment.status.name}")
                                }
                                Text("Date: ${payment.paymentDate}")
                                if (!payment.remarks.isNullOrBlank()) {
                                    Text("Remarks: ${payment.remarks}")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DealDateField(viewModel: UnitEditViewModel) {
    DatePickerField(
        value = viewModel.uiState.dealDate,
        onValueChange = { newDate ->
            viewModel.uiState = viewModel.uiState.copy(
                dealDate = newDate
            )
        },
        isError = viewModel.uiState.dealDate.isEmpty() && viewModel.uiState.showValidationError,
        label = "Deal Date *"
    )
}

@Composable
fun ClosingDateField(viewModel: UnitEditViewModel) {
    DatePickerField(
        value = viewModel.uiState.closingDate,
        onValueChange = { newDate ->
            viewModel.uiState = viewModel.uiState.copy(
                closingDate = newDate
            )
        },
        isError = false, // Not required field
        label = "Closing Date (Optional)"
    )
}

@Composable
fun UnitCreationDateField(viewModel: UnitEditViewModel) {
    DatePickerField(
        value = viewModel.uiState.unitCreationDate,
        onValueChange = { newDate ->
            viewModel.uiState = viewModel.uiState.copy(
                unitCreationDate = newDate
            )
        },
        isError = viewModel.uiState.unitCreationDate.isEmpty() && viewModel.uiState.showValidationError,
        label = "Unit Creation Date *"
    )
}

@Composable
fun PaymentDateField(viewModel: UnitEditViewModel) {
    DatePickerField(
        value = viewModel.uiState.paymentDate,
        onValueChange = { newDate ->
            viewModel.uiState = viewModel.uiState.copy(
                paymentDate = newDate
            )
        },
        isError = viewModel.uiState.paymentDate.isEmpty() && viewModel.uiState.showValidationError,
        label = "Payment Date *"
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    label: String = "Payment Date *"
) {
    var showDatePicker by remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }

    // Format for displaying and parsing dates
    val dateFormatter = SimpleDateFormat("MM/dd/yyyy", Locale.US)

    OutlinedTextField(
        value = value,
        onValueChange = { /* Read-only field, user can't type directly */ },
        label = { Text(label) },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged {
                if (it.isFocused) showDatePicker = true
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) { showDatePicker = true },
        isError = isError,
        readOnly = true,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Select date",
                modifier = Modifier.clickable { showDatePicker = true }
            )
        }
    )

    if (showDatePicker) {
        // Parse existing date or use current date
        val initialDate = try {
            if (value.isNotEmpty()) dateFormatter.parse(value)?.time else null
        } catch (e: Exception) {
            null
        }

        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = initialDate ?: System.currentTimeMillis()
        )

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { dateMillis ->
                        val date = Date(dateMillis)
                        onValueChange(dateFormatter.format(date))
                    }
                    showDatePicker = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }
}