package com.prafullkumar.propvault.admin.features.developments.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafullkumar.propvault.admin.features.developments.presentation.viewmodels.AddDevelopmentViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDevelopmentScreen(
    viewModel: AddDevelopmentViewModel,
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Development") },
                navigationIcon = {
                    IconButton(onClick = navController::popBackStack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FormCard {
                    FormSectionTitle(title = "Basic Information")
                    CustomTextField(
                        value = viewModel.name,
                        onValueChange = viewModel::updateName,
                        label = "Development Name",
                        isRequired = true
                    )

                    DropdownField(
                        value = viewModel.category,
                        onValueChange = viewModel::updateCategory,
                        label = "Category",
                        options = viewModel.categoryOptions,
                        isRequired = true
                    )

                    CustomTextField(
                        value = viewModel.website,
                        onValueChange = viewModel::updateWebsite,
                        label = "Website"
                    )

                    CustomTextField(
                        value = viewModel.city,
                        onValueChange = viewModel::updateCity,
                        label = "City",
                        isRequired = true
                    )
                }

                FormCard {
                    FormSectionTitle(title = "Business Details")

                    CustomTextField(
                        value = viewModel.attorneyFirmName,
                        onValueChange = viewModel::updateAttorneyFirmName,
                        label = "Attorney Firm Name"
                    )

                    CustomTextField(
                        value = viewModel.brokerageFeeCommission,
                        onValueChange = viewModel::updateBrokerageFeeCommission,
                        label = "Brokerage Fee Commission (%)",
                        keyboardType = KeyboardType.Number
                    )

                    DropdownField(
                        value = viewModel.status,
                        onValueChange = viewModel::updateStatus,
                        label = "Status",
                        options = viewModel.statusOptions,
                        isRequired = true
                    )
                }

                FormCard {
                    FormSectionTitle(title = "Property Details")

                    CustomTextField(
                        value = viewModel.startingPrice,
                        onValueChange = viewModel::updateStartingPrice,
                        label = "Starting Price ($)",
                        keyboardType = KeyboardType.Decimal
                    )

                    CustomTextField(
                        value = viewModel.numberOfBuildings,
                        onValueChange = viewModel::updateNumberOfBuildings,
                        label = "Number of Buildings",
                        keyboardType = KeyboardType.Number
                    )

                    CustomTextField(
                        value = viewModel.imageUrl,
                        onValueChange = viewModel::updateImageUrl,
                        label = "Image URL"
                    )
                }

                // Error message display
                viewModel.errorMessage?.let { error ->
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                if (viewModel.isSuccess) run {
                    navController.popBackStack()
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(
                        onClick = { viewModel.clearFields() },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Clear Form")
                    }

                    Button(
                        onClick = {
                            scope.launch {
                                viewModel.saveDevelopment()
                            }
                        },
                        enabled = !viewModel.isSaving,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        if (viewModel.isSaving) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text("Save Development")
                        }
                    }
                }

                // Bottom spacing
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun DropdownField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    options: List<String>,
    isRequired: Boolean = false
) {
    val displayLabel = if (isRequired) "$label *" else label
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Box {
            OutlinedTextField(
                value = value,
                onValueChange = { },
                label = { Text(displayLabel) },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                trailingIcon = {
                    IconButton(onClick = { expanded = true }) {
                        Text(
                            text = "▼",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )

            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clickable { expanded = true }
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.surface)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(4.dp)
                    )
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onValueChange(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}