package com.prafullkumar.propvault.admin.features.home.presentation.screens


import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Money
import androidx.compose.material.icons.filled.Pending
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafullkumar.common.models.Deal
import com.prafullkumar.common.models.Payment
import com.prafullkumar.common.models.PaymentStatus
import com.prafullkumar.common.navigation.MainRoutes
import com.prafullkumar.common.presentation.ErrorScreen
import com.prafullkumar.common.presentation.LoadingScreen
import com.prafullkumar.common.presentation.SignOutDialog
import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.admin.app.navigation.AdminRoutes
import com.prafullkumar.propvault.admin.features.home.presentation.viewmodels.HomeViewModel
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar


@Composable
fun HomeScreen(
    navController: NavController, viewModel: HomeViewModel
) {

    val adminDetails by viewModel.adminDetails.collectAsState()
    val recentDeals by viewModel.recentDeals.collectAsState()
    val recentPayments by viewModel.recentPayments.collectAsState()

    if (recentPayments is Resource.Error || recentDeals is Resource.Error || adminDetails is Resource.Error) {
        ErrorScreen()
    } else if (recentPayments is Resource.Loading || recentDeals is Resource.Loading || adminDetails is Resource.Loading) {
        LoadingScreen()
    } else {
        val adminDetailsData = (adminDetails as Resource.Success).data
        val recentDealsData = (recentDeals as Resource.Success).data
        val payments = (recentPayments as Resource.Success).data
        HomeDashboard(
            developerStats = DashboardStats(
                totalSales = adminDetailsData.totalSales,
                totalDevelopments = adminDetailsData.totalDevelopments,
                totalDeals = adminDetailsData.totalDeals
            ),
            recentDeals = recentDealsData,
            recentPayments = payments,
            onViewAllPayments = { navController.navigate(AdminRoutes.PaymentsScreen) },
            navController = navController,
            viewModel = viewModel
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDashboard(
    modifier: Modifier = Modifier,
    developerStats: DashboardStats = DashboardStats(),
    recentDeals: List<Deal> = emptyList(),
    recentPayments: List<Payment> = emptyList(),
    onViewAllPayments: () -> Unit = {},
    navController: NavController,
    viewModel: HomeViewModel
) {
    val currencyFormatter = remember { NumberFormat.getCurrencyInstance() }
    var showSignOutDialog by remember {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Real Estate CRM") }, actions = {
                IconButton(onClick = {
                    showSignOutDialog = !showSignOutDialog
                }) {
                    Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "SignOut")
                }
            })
        }) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            // Greeting
            item {
                val currentTime = remember { LocalTime.now() }
                val greeting = when {
                    currentTime.hour < 12 -> "Good Morning"
                    currentTime.hour < 17 -> "Good Afternoon"
                    else -> "Good Evening"
                }

                Text(
                    text = "$greeting!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Here's your real estate activity summary",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            // Key Stats Cards
            item {
                StatsCardRow(developerStats, currencyFormatter)
            }

            // Recent Deals Section
            item {
                SectionHeader(
                    title = "Recent Deals", showViewALl = false
                )
            }

            item {
                if (recentDeals.isEmpty()) {
                    EmptyStateCard(
                        message = "No recent deals found", icon = Icons.Default.Info
                    )
                } else {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(recentDeals.take(5)) { deal ->
                            DealCard(deal, currencyFormatter)
                        }
                    }
                }
            }

            // Payment History
            item {
                SectionHeader(
                    title = "Recent Payments", onViewAll = onViewAllPayments
                )
            }

            item {
                if (recentPayments.isEmpty()) {
                    EmptyStateCard(
                        message = "No recent payments found", icon = Icons.Default.Money
                    )
                } else {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        recentPayments.take(3).forEach { payment ->
                            PaymentHistoryItem(payment, currencyFormatter)
                        }
                    }
                }
            }
        }
    }
    if (showSignOutDialog) {
        SignOutDialog(onConfirmClick = {
            showSignOutDialog = false
            viewModel.signOut()
            navController.navigate(MainRoutes.OnBoarding) {
                popUpTo(AdminRoutes.Home) {
                    inclusive = true
                }
            }
        }) { }
    }
}

@Composable
fun StatsCardRow(stats: DashboardStats, currencyFormatter: NumberFormat) {
    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        StatCard(
            title = "Total Sales",
            value = currencyFormatter.format(stats.totalSales),
            icon = Icons.Default.AttachMoney,
            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.weight(1f)
        )

        StatCard(
            title = "Deals",
            value = "${stats.totalDeals}",
            icon = Icons.Default.Handshake,
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier.weight(1f)
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    Row(
        modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        StatCard(
            title = "Developments",
            value = "${stats.totalDevelopments}",
            icon = Icons.Default.LocationCity,
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
            modifier = Modifier.weight(1f)
        )

    }
}

@Composable
fun StatCard(
    title: String,
    value: String,
    icon: ImageVector,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(100.dp), colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            }

            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun SectionHeader(
    title: String, showViewALl: Boolean = true, onViewAll: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold
        )

        if (showViewALl) {
            TextButton(onClick = onViewAll) {
                Text("View All")
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun DealCard(deal: Deal, currencyFormatter: NumberFormat) {
    Card(
        modifier = Modifier.width(280.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = deal.customerName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                StatusChip(status = deal.status.name)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Deal Date",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Text(
                        text = formatDate(deal.dealDate),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Price",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Text(
                        text = currencyFormatter.format(deal.totalPrice),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = deal.assignedAgent,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}

@Composable
fun StatusChip(status: String) {
    val chipColor = when (status) {
        "COMPLETED" -> MaterialTheme.colorScheme.primary
        "PENDING" -> MaterialTheme.colorScheme.tertiary
        "CANCELLED" -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.secondary
    }

    Surface(
        modifier = Modifier.height(24.dp),
        shape = RoundedCornerShape(12.dp),
        color = chipColor.copy(alpha = 0.15f)
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 8.dp), contentAlignment = Alignment.Center
        ) {
            Text(
                text = status.lowercase().replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.bodySmall,
                color = chipColor,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun PaymentHistoryItem(payment: Payment, currencyFormatter: NumberFormat) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
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

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = formatDate(payment.paymentDate),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = currencyFormatter.format(payment.amount),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    StatusChip(status = payment.status.name)
                }
            }
        }
    }
}

@Composable
fun EmptyStateCard(message: String, icon: ImageVector) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
            )
        }
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

// Enum for LocalTime (replicating java.time.LocalTime functionality)
data class LocalTime(val hour: Int, val minute: Int, val second: Int) {
    companion object {
        fun now(): LocalTime {
            val calendar = java.util.Calendar.getInstance()
            return LocalTime(
                hour = calendar.get(Calendar.HOUR_OF_DAY),
                minute = calendar.get(Calendar.MINUTE),
                second = calendar.get(Calendar.SECOND)
            )
        }
    }
}


// Data classes for dashboard
data class DashboardStats(
    val totalSales: Double = 0.0,
    val totalDeals: Int = 0,
    val totalDevelopments: Int = 0,
)

data class DevelopmentSummary(
    val id: Int = 0,
    val name: String = "",
    val category: String = "",
    val city: String = "",
    val startingPrice: Double = 0.0,
    val totalUnits: Int = 0,
    val unitsSold: Int = 0,
    val totalSales: Double = 0.0
)