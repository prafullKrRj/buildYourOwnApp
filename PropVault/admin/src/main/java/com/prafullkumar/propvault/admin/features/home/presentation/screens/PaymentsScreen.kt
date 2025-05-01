package com.prafullkumar.propvault.admin.features.home.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoneyOff
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafullkumar.common.models.Payment
import com.prafullkumar.common.models.PaymentStatus
import com.prafullkumar.propvault.admin.features.home.domain.model.PaymentWithUnitAndDeal
import com.prafullkumar.propvault.admin.features.home.presentation.viewmodels.PaymentViewModel
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun PaymentScreen(
    viewModel: PaymentViewModel,
    navController: NavController
) {
    val payments by viewModel.payments.collectAsState()
    PaymentListScreen(payments, onBackClick = {
        navController.popBackStack()
    })
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentListScreen(
    payments: List<PaymentWithUnitAndDeal>,
    onPaymentClick: (Payment) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val currencyFormatter = remember { NumberFormat.getCurrencyInstance() }
    var filterStatus by remember { mutableStateOf<PaymentStatus?>(null) }
    var sortOrder by remember { mutableStateOf(SortOrder.NEWEST_FIRST) }

    val filteredPayments = remember(payments, filterStatus, sortOrder) {
        var result = payments

        // Apply filter
        if (filterStatus != null) {
            result = result.filter { it.payment.status == filterStatus }
        }

        // Apply sort
        result = when (sortOrder) {
            SortOrder.NEWEST_FIRST -> result.sortedBy { it.payment.paymentDate }
            SortOrder.OLDEST_FIRST -> result.sortedByDescending { it.payment.paymentDate }
            SortOrder.AMOUNT_HIGH_TO_LOW -> result.sortedByDescending { it.payment.amount }
            SortOrder.AMOUNT_LOW_TO_HIGH -> result.sortedBy { it.payment.amount }
        }

        result
    }

    val totalPayments = filteredPayments.sumOf { it.payment.amount }
    val pendingAmount = filteredPayments
        .filter { it.payment.status == PaymentStatus.PENDING }
        .sumOf { it.payment.amount }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Payments") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { /* Search Functionality */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }

                    var showFilterOptions by remember { mutableStateOf(false) }

                    Box {
                        IconButton(onClick = { showFilterOptions = !showFilterOptions }) {
                            Icon(Icons.Default.FilterList, contentDescription = "Filter")
                        }

                        DropdownMenu(
                            expanded = showFilterOptions,
                            onDismissRequest = { showFilterOptions = false }
                        ) {
                            Text(
                                "Filter by Status",
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                fontWeight = FontWeight.Bold
                            )

                            DropdownMenuItem(
                                text = { Text("All Payments") },
                                onClick = {
                                    filterStatus = null
                                    showFilterOptions = false
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.List,
                                        contentDescription = null,
                                        tint = if (filterStatus == null)
                                            MaterialTheme.colorScheme.primary else
                                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    )
                                }
                            )

                            DropdownMenuItem(
                                text = { Text("Completed") },
                                onClick = {
                                    filterStatus = PaymentStatus.COMPLETED
                                    showFilterOptions = false
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.CheckCircle,
                                        contentDescription = null,
                                        tint = if (filterStatus == PaymentStatus.COMPLETED)
                                            MaterialTheme.colorScheme.primary else
                                            Color(0xFF4CAF50)
                                    )
                                }
                            )

                            DropdownMenuItem(
                                text = { Text("Pending") },
                                onClick = {
                                    filterStatus = PaymentStatus.PENDING
                                    showFilterOptions = false
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.Pending,
                                        contentDescription = null,
                                        tint = if (filterStatus == PaymentStatus.PENDING)
                                            MaterialTheme.colorScheme.primary else
                                            Color(0xFFFFA000)
                                    )
                                }
                            )

                            DropdownMenuItem(
                                text = { Text("Failed") },
                                onClick = {
                                    filterStatus = PaymentStatus.FAILED
                                    showFilterOptions = false
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.Error,
                                        contentDescription = null,
                                        tint = if (filterStatus == PaymentStatus.FAILED)
                                            MaterialTheme.colorScheme.primary else
                                            Color(0xFFF44336)
                                    )
                                }
                            )

                            Divider()

                            Text(
                                "Sort by",
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                fontWeight = FontWeight.Bold
                            )

                            DropdownMenuItem(
                                text = { Text("Newest First") },
                                onClick = {
                                    sortOrder = SortOrder.NEWEST_FIRST
                                    showFilterOptions = false
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.DateRange,
                                        contentDescription = null,
                                        tint = if (sortOrder == SortOrder.NEWEST_FIRST)
                                            MaterialTheme.colorScheme.primary else
                                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    )
                                }
                            )

                            DropdownMenuItem(
                                text = { Text("Oldest First") },
                                onClick = {
                                    sortOrder = SortOrder.OLDEST_FIRST
                                    showFilterOptions = false
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.DateRange,
                                        contentDescription = null,
                                        tint = if (sortOrder == SortOrder.OLDEST_FIRST)
                                            MaterialTheme.colorScheme.primary else
                                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    )
                                }
                            )

                            DropdownMenuItem(
                                text = { Text("Amount: High to Low") },
                                onClick = {
                                    sortOrder = SortOrder.AMOUNT_HIGH_TO_LOW
                                    showFilterOptions = false
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.TrendingDown,
                                        contentDescription = null,
                                        tint = if (sortOrder == SortOrder.AMOUNT_HIGH_TO_LOW)
                                            MaterialTheme.colorScheme.primary else
                                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    )
                                }
                            )

                            DropdownMenuItem(
                                text = { Text("Amount: Low to High") },
                                onClick = {
                                    sortOrder = SortOrder.AMOUNT_LOW_TO_HIGH
                                    showFilterOptions = false
                                },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.TrendingUp,
                                        contentDescription = null,
                                        tint = if (sortOrder == SortOrder.AMOUNT_LOW_TO_HIGH)
                                            MaterialTheme.colorScheme.primary else
                                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    )
                                }
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Payment summary cards
            PaymentSummaryCards(
                totalAmount = totalPayments,
                pendingAmount = pendingAmount,
                currencyFormatter = currencyFormatter,
                modifier = Modifier.padding(16.dp)
            )

            // Status filter chips
            StatusFilterChips(
                selectedStatus = filterStatus,
                onStatusSelected = { filterStatus = it },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
            )

            // Payment list
            if (filteredPayments.isEmpty()) {
                EmptyPaymentsList(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredPayments, key = { it.payment.id }) { paymentWithData ->
                        PaymentListItem(
                            paymentWithData = paymentWithData,
                            currencyFormatter = currencyFormatter,
                            onClick = { onPaymentClick(paymentWithData.payment) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentSummaryCards(
    totalAmount: Double,
    pendingAmount: Double,
    currencyFormatter: NumberFormat,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        SummaryCard(
            title = "Total Payments",
            value = currencyFormatter.format(totalAmount),
            icon = Icons.Default.Payments,
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.weight(1f),
            maxLines = 1
        )

        SummaryCard(
            title = "Pending",
            value = currencyFormatter.format(pendingAmount),
            icon = Icons.Default.Pending,
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.weight(1f),
            maxLines = 1
        )
    }
}

@Composable
fun SummaryCard(
    title: String,
    value: String,
    icon: ImageVector,
    backgroundColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    maxLines: Int = 2
) {
    Card(
        modifier = modifier.height(100.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = contentColor.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = contentColor.copy(alpha = 0.8f),
                    maxLines = maxLines,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = contentColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatusFilterChips(
    selectedStatus: PaymentStatus?,
    onStatusSelected: (PaymentStatus?) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip(
            selected = selectedStatus == null,
            onClick = { onStatusSelected(null) },
            label = { Text("All") },
            leadingIcon = {
                if (selectedStatus == null) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                    )
                }
            }
        )

        FilterChip(
            selected = selectedStatus == PaymentStatus.COMPLETED,
            onClick = { onStatusSelected(PaymentStatus.COMPLETED) },
            label = { Text("Completed") },
            leadingIcon = {
                if (selectedStatus == PaymentStatus.COMPLETED) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                    )
                }
            }
        )

        FilterChip(
            selected = selectedStatus == PaymentStatus.PENDING,
            onClick = { onStatusSelected(PaymentStatus.PENDING) },
            label = { Text("Pending") },
            leadingIcon = {
                if (selectedStatus == PaymentStatus.PENDING) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                    )
                }
            }
        )

        FilterChip(
            selected = selectedStatus == PaymentStatus.FAILED,
            onClick = { onStatusSelected(PaymentStatus.FAILED) },
            label = { Text("Failed") },
            leadingIcon = {
                if (selectedStatus == PaymentStatus.FAILED) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(FilterChipDefaults.IconSize)
                    )
                }
            }
        )
    }
}

@Composable
fun PaymentListItem(
    paymentWithData: PaymentWithUnitAndDeal,
    currencyFormatter: NumberFormat,
    onClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(if (expanded) 180f else 0f)

    val payment = paymentWithData.payment
    val deal = paymentWithData.deal
    val unit = paymentWithData.unit

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Upper part - always visible
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Status indicator
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(getPaymentStatusColor(payment.status).copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = getPaymentStatusIcon(payment.status),
                            contentDescription = null,
                            tint = getPaymentStatusColor(payment.status)
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Payment details

                    Column {
                        Text(
                            text = formatDate(payment.paymentDate),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        )
                    }

                }

                // Amount and expand button
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = currencyFormatter.format(payment.amount),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        StatusChip(payment.status.name)
                    }

                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            imageVector = Icons.Default.ExpandMore,
                            contentDescription = if (expanded) "Show less" else "Show more",
                            modifier = Modifier.rotate(rotationState)
                        )
                    }
                }
            }

            // Lower part - expandable details
            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    // Deal & Unit information
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Unit details
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Unit Details",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = unit.name,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )

                            Text(
                                text = "Code: ${unit.unitCode}",
                                style = MaterialTheme.typography.bodySmall
                            )

                            Text(
                                text = "Building: ${unit.buildingName}",
                                style = MaterialTheme.typography.bodySmall,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                        // Customer details
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Customer",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = deal.customerName,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium
                            )

                            Text(
                                text = deal.customerPhone,
                                style = MaterialTheme.typography.bodySmall
                            )

                            Text(
                                text = deal.customerEmail,
                                style = MaterialTheme.typography.bodySmall,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Payment details
                    Column {
                        Text(
                            text = "Payment Details",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                PaymentDetailRow(
                                    label = "Total Price",
                                    value = currencyFormatter.format(payment.totalPrice)
                                )

                                PaymentDetailRow(
                                    label = "Deposited",
                                    value = currencyFormatter.format(payment.depositedAmount)
                                )
                            }

                            Column(modifier = Modifier.weight(1f)) {
                                PaymentDetailRow(
                                    label = "Amount Paid",
                                    value = currencyFormatter.format(payment.amount)
                                )

                                PaymentDetailRow(
                                    label = "Due Amount",
                                    value = currencyFormatter.format(payment.dueAmount)
                                )
                            }
                        }
                    }

                    // Remarks
                    payment.remarks?.let {
                        if (it.isNotBlank()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Remarks",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Action buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(onClick = { /* View Receipt */ }) {
                            Icon(
                                imageVector = Icons.Default.Receipt,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Receipt")
                        }

                        FilledTonalButton(onClick = onClick) {
                            Text("Details")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 2.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun EmptyPaymentsList(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.MoneyOff,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "No payments found",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Adjust your filters or create new payment records",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )
    }
}

// Helper functions and data classes
private fun formatDate(dateString: String): String {
    return try {
        val date = LocalDate.parse(dateString)
        date.format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))
    } catch (e: Exception) {
        dateString
    }
}

private fun getPaymentStatusColor(status: PaymentStatus): Color {
    return when (status) {
        PaymentStatus.COMPLETED -> Color(0xFF4CAF50)
        PaymentStatus.PENDING -> Color(0xFFFFA000)
        PaymentStatus.FAILED -> Color(0xFFF44336)
    }
}

private fun getPaymentStatusIcon(status: PaymentStatus): ImageVector {
    return when (status) {
        PaymentStatus.COMPLETED -> Icons.Default.CheckCircle
        PaymentStatus.PENDING -> Icons.Default.Pending
        PaymentStatus.FAILED -> Icons.Default.Error
    }
}


enum class SortOrder {
    NEWEST_FIRST,
    OLDEST_FIRST,
    AMOUNT_HIGH_TO_LOW,
    AMOUNT_LOW_TO_HIGH
}