package com.prafullkumar.propvault.customer.app.presentation.screens

import android.icu.text.NumberFormat
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.prafullkumar.common.models.PropertyUnit
import com.prafullkumar.common.navigation.MainRoutes
import com.prafullkumar.common.presentation.ErrorScreen
import com.prafullkumar.common.presentation.LoadingScreen
import com.prafullkumar.common.presentation.SignOutDialog
import com.prafullkumar.common.sampleData.propertyUnits
import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.customer.app.presentation.navigation.CustomerRoutes
import com.prafullkumar.propvault.customer.app.presentation.viewmodels.PropertyListViewModel

enum class SortOption {
    NAME_ASC, PRICE_ASC, PRICE_DESC, NEWEST
}

@Composable
fun PropertyListScreen(
    viewModel: PropertyListViewModel, navController: NavController
) {
    val properties by viewModel.properties.collectAsState()
    when (properties) {
        is Resource.Error -> ErrorScreen()
        Resource.Loading -> LoadingScreen()
        is Resource.Success<List<PropertyUnit>> -> {
            PropertyListScreenSuccess(
                viewModel = viewModel,
                navController = navController,
                properties = (properties as Resource.Success<List<PropertyUnit>>).data
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyListScreenSuccess(
    viewModel: PropertyListViewModel, navController: NavController, properties: List<PropertyUnit>
) {
    var currentSortOption by remember { mutableStateOf(SortOption.NAME_ASC) }
    var showFilters by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }


    val filteredProperties = remember(currentSortOption, properties, searchQuery) {
        propertyUnits.filter { it.status != "SOLD" }.filter {
            if (searchQuery.isEmpty()) true
            else it.name.contains(
                searchQuery,
                ignoreCase = true
            ) || it.developmentName.contains(
                searchQuery,
                ignoreCase = true
            ) || it.buildingName.contains(
                searchQuery,
                ignoreCase = true
            ) || it.unitType.contains(searchQuery, ignoreCase = true)
        }.sortedWith(
            when (currentSortOption) {
                SortOption.NAME_ASC -> compareBy { it.name }
                SortOption.PRICE_ASC -> compareBy { it.price }
                SortOption.PRICE_DESC -> compareByDescending { it.price }
                SortOption.NEWEST -> compareByDescending { it.createdTime }
            })
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    "Luxury Properties", style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }, actions = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(onClick = { showFilters = !showFilters }) {
                        Icon(
                            imageVector = Icons.Default.Sort, contentDescription = "Sort"
                        )
                    }

                    IconButton(onClick = {
                        showDialog = true
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Logout"
                        )
                    }
                }
            })
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Search properties...") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search, contentDescription = "Search"
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(
                                imageVector = Icons.Default.Clear, contentDescription = "Clear"
                            )
                        }
                    }
                },
                singleLine = true
            )

            AnimatedVisibility(
                visible = showFilters, enter = fadeIn(), exit = fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Sort By",
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    SortRadioGroup(
                        selectedOption = currentSortOption,
                        onOptionSelected = { currentSortOption = it })

                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    )
                }
            }

            if (filteredProperties.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.SearchOff,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp)
                        )
                        Text(
                            text = "No properties found",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = "Try adjusting your search"
                        )
                    }
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(filteredProperties) { property ->
                        PropertyCard(property = property, navController)
                    }
                }
            }
        }
    }
    if (showDialog) {
        SignOutDialog(onConfirmClick = {
            showDialog = false
            viewModel.signOut()
            navController.navigate(MainRoutes.OnBoarding) {
                popUpTo(CustomerRoutes.Home) {
                    inclusive = true
                }
            }
        }) {
            showDialog = false
        }
    }
}

@Composable
fun PropertyCard(property: PropertyUnit, navController: NavController) {
    var expanded by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f, animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow
        )
    )

    val formatter = remember { NumberFormat.getCurrencyInstance() }
    val formattedPrice = remember(property.price) { formatter.format(property.price) }

    Card(
        modifier = Modifier.fillMaxWidth(), onClick = {
            navController.navigate(CustomerRoutes.PropertyDetailsScreen(propertyId = property.id))
        }) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = property.name, fontWeight = FontWeight.Bold, fontSize = 20.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )

                Text(
                    text = "${property.buildingName}, ${property.developmentName}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = formattedPrice, fontWeight = FontWeight.Bold, fontSize = 18.sp
            )

            AnimatedVisibility(visible = expanded) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Divider()
                    Spacer(modifier = Modifier.height(16.dp))

                    DetailRow(
                        label = "Development",
                        value = property.developmentName,
                        icon = Icons.Default.Apartment
                    )

                    DetailRow(
                        label = "Building",
                        value = property.buildingName,
                        icon = Icons.Default.Business
                    )

                    DetailRow(
                        label = "Unit Type",
                        value = property.unitType,
                        icon = Icons.Default.Category
                    )

                    DetailRow(
                        label = "Created",
                        value = property.createdTime,
                        icon = Icons.Default.DateRange
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { /* Contact action */ }, modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = null,
                            modifier = Modifier.size(ButtonDefaults.IconSize)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Inquire About This Property")
                    }
                }
            }

            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = { expanded = !expanded }, modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.ExpandMore,
                        contentDescription = if (expanded) "Collapse" else "Expand",
                        modifier = Modifier.rotate(rotationState)
                    )
                }
            }
        }
    }
}

@Composable
fun DetailRow(
    label: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon, contentDescription = null, modifier = Modifier.size(20.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = label, fontSize = 12.sp
            )

            Text(
                text = value, fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun SortRadioGroup(
    selectedOption: SortOption, onOptionSelected: (SortOption) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SortRadioButton(
            text = "Name (A-Z)",
            selected = selectedOption == SortOption.NAME_ASC,
            onClick = { onOptionSelected(SortOption.NAME_ASC) })

        SortRadioButton(
            text = "Price (Low to High)",
            selected = selectedOption == SortOption.PRICE_ASC,
            onClick = { onOptionSelected(SortOption.PRICE_ASC) })

        SortRadioButton(
            text = "Price (High to Low)",
            selected = selectedOption == SortOption.PRICE_DESC,
            onClick = { onOptionSelected(SortOption.PRICE_DESC) })

        SortRadioButton(
            text = "Newest First",
            selected = selectedOption == SortOption.NEWEST,
            onClick = { onOptionSelected(SortOption.NEWEST) })
    }
}

@Composable
fun SortRadioButton(
    text: String, selected: Boolean, onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected, onClick = onClick
        )

        Text(
            text = text, modifier = Modifier.padding(start = 8.dp)
        )
    }
}