package com.prafullkumar.propvault.admin.features.deals.presentation.screens


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.prafullkumar.common.models.Deal
import com.prafullkumar.common.models.DealStatus
import com.prafullkumar.common.presentation.ErrorScreen
import com.prafullkumar.common.presentation.LoadingScreen
import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.admin.app.navigation.AdminRoutes
import com.prafullkumar.propvault.admin.features.deals.presentation.viewmodels.DealsViewModel
import java.text.NumberFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DealsScreen(
    viewModel: DealsViewModel, navController: NavController
) {
    val deals by viewModel.deals.collectAsState()
    when (deals) {
        is Resource.Error -> {
            ErrorScreen()
        }

        Resource.Loading -> {
            LoadingScreen()
        }

        is Resource.Success<*> -> {
            val dealsList = (deals as Resource.Success<List<Deal>>).data
            DealsSuccessScreen(deals = dealsList, navController = navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DealsSuccessScreen(
    deals: List<Deal>, navController: NavController
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedStatus by remember { mutableStateOf<DealStatus?>(null) }

    val filteredDeals = remember(deals, searchQuery, selectedStatus) {
        deals.filter { deal ->
            val matchesSearch = searchQuery.isEmpty() || deal.customerName.contains(
                searchQuery, ignoreCase = true
            ) || deal.assignedAgent.contains(searchQuery, ignoreCase = true)

            val matchesStatus = selectedStatus == null || deal.status == selectedStatus

            matchesSearch && matchesStatus
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Deals") },
            )
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            SearchAndFilterBar(
                searchQuery = searchQuery,
                onSearchQueryChange = { searchQuery = it },
                selectedStatus = selectedStatus,
                onStatusSelected = { selectedStatus = it })

            Spacer(modifier = Modifier.height(16.dp))

            if (deals.isEmpty()) {
                EmptyDealsView()
            } else {
                DealsList(deals = filteredDeals, navController)
            }
        }
    }
}

@Composable
fun SearchAndFilterBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedStatus: DealStatus?,
    onStatusSelected: (DealStatus?) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier.weight(1f),
            placeholder = { Text("Search by customer or agent") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search, contentDescription = "Search"
                )
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.width(8.dp))

        StatusFilterDropdown(
            selectedStatus = selectedStatus, onStatusSelected = onStatusSelected
        )
    }
}

@Composable
fun StatusFilterDropdown(
    selectedStatus: DealStatus?, onStatusSelected: (DealStatus?) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        IconButton(
            onClick = { expanded = true },
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(
                    color = if (selectedStatus != null) MaterialTheme.colorScheme.primaryContainer
                    else MaterialTheme.colorScheme.surfaceVariant
                )
        ) {
            Icon(
                imageVector = Icons.Default.FilterList,
                contentDescription = "Filter by status",
                tint = if (selectedStatus != null) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        DropdownMenu(
            expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(text = { Text("All Statuses") }, onClick = {
                onStatusSelected(null)
                expanded = false
            })

            DealStatus.entries.forEach { status ->
                DropdownMenuItem(text = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        StatusIndicator(status = status)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(status.name)
                    }
                }, onClick = {
                    onStatusSelected(status)
                    expanded = false
                })
            }
        }
    }
}

@Composable
fun EmptyDealsView() {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No deals available", style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Deals will appear here once created",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun DealsList(deals: List<Deal>, navController: NavController) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp), contentPadding = PaddingValues(12.dp)
    ) {
        items(deals.sortedBy {
            it.dealDate
        }, key = { item: Deal ->
            item.id
        }) { deal ->
            DealCard(deal = deal, navController)
        }
    }
}

@Composable
fun DealCard(deal: Deal, navController: NavController) {
    var expanded by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f
    )

    Card(
        modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ), shape = RoundedCornerShape(12.dp), onClick = {
            navController.navigate(
                AdminRoutes.DealScreen(
                    deal.id
                )
            )
        }) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    StatusIndicator(status = deal.status)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = deal.customerName,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = formatCurrency(deal.totalPrice),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = deal.assignedAgent, style = MaterialTheme.typography.bodyMedium
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = deal.dealDate, style = MaterialTheme.typography.bodyMedium
                    )
                }

                IconButton(
                    onClick = { expanded = !expanded }, modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (expanded) "Show less" else "Show more",
                        modifier = Modifier.rotate(rotationState)
                    )
                }
            }

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Divider()
                    Spacer(modifier = Modifier.height(16.dp))

                    CustomerInfoSection(
                        phone = deal.customerPhone, email = deal.customerEmail
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    DealDetailsSection(
                        closingDate = deal.closingDate, remarks = deal.remarks
                    )
                }
            }
        }
    }
}

@Composable
fun CustomerInfoSection(
    phone: String, email: String
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Customer Information",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Call,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = phone, style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = email,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun DealDetailsSection(
    closingDate: String?, remarks: String?
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Deal Details",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        closingDate?.let {
            DetailItem(title = "Closing Date", value = it)
            Spacer(modifier = Modifier.height(8.dp))
        }

        remarks?.let {
            DetailItem(title = "Remarks", value = it)
        }
    }
}

@Composable
private fun DetailItem(title: String, value: String) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value, style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun StatusIndicator(status: DealStatus) {
    val (color, progressValue) = when (status) {
        DealStatus.PENDING -> Pair(Color(0xFFFFA000), 0.25f) // Amber
        DealStatus.ACTIVE -> Pair(Color(0xFF2196F3), 0.5f) // Blue
        DealStatus.COMPLETED -> Pair(Color(0xFF4CAF50), 1f) // Green
        DealStatus.CANCELLED -> Pair(Color(0xFFF44336), 0f) // Red
    }

    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .border(2.dp, color, CircleShape)
            .padding(4.dp), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = progressValue,
            color = color,
            modifier = Modifier.fillMaxSize(),
            strokeWidth = 2.dp
        )

        // Status initial letter
        Text(
            text = status.name[0].toString(),
            color = color,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

// Helper function to format currency
fun formatCurrency(amount: Double): String {
    val format = NumberFormat.getCurrencyInstance()
    return format.format(amount)
}