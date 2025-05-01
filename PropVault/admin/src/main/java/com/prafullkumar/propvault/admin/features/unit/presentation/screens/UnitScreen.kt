package com.prafullkumar.propvault.admin.features.unit.presentation.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Apartment
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.Business
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.QrCode
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafullkumar.common.presentation.ErrorScreen
import com.prafullkumar.common.presentation.LoadingScreen
import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.admin.app.navigation.AdminRoutes
import com.prafullkumar.propvault.admin.features.deals.presentation.screens.DealOverviewCard
import com.prafullkumar.propvault.admin.features.deals.presentation.screens.PaymentCard
import com.prafullkumar.propvault.admin.features.unit.domain.UnitWithDealsAndPayments
import com.prafullkumar.propvault.admin.features.unit.presentation.viewmodels.UnitViewModel
import java.text.NumberFormat

@Composable
fun UnitScreen(
    viewModel: UnitViewModel, navController: NavController
) {
    val state by viewModel.unitWithDetails.collectAsState()
    when (state) {
        is Resource.Error -> {
            ErrorScreen()
        }

        Resource.Loading -> {
            LoadingScreen()
        }

        is Resource.Success<UnitWithDealsAndPayments> -> {
            val unit = (state as Resource.Success<UnitWithDealsAndPayments>).data
            Column {
                UnitDetailsScreen(
                    unitWithDetails = unit,
                    onBackPressed = navController::popBackStack,
                    onEditClicked = {
                        navController.navigate(AdminRoutes.EditUnit(unit.unit.id))
                    }
                )
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitDetailsScreen(
    unitWithDetails: UnitWithDealsAndPayments, onBackPressed: () -> Unit, onEditClicked: () -> Unit
) {
    val scrollState = rememberScrollState()
    val currencyFormatter = NumberFormat.getCurrencyInstance()
    val unit = unitWithDetails.unit
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Unit Details") }, navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }, actions = {
                    IconButton(onClick = onEditClicked) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                    }
                }
            )
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            // Unit header with price tag
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = unit.name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    Text(
                        text = unit.unitCode,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                    )
                }

                Card(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(8.dp),
                    border = CardDefaults.outlinedCardBorder()
                ) {
                    Text(
                        text = currencyFormatter.format(unit.price),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }

            // Status indicator
            val statusColor = when (unit.status.lowercase()) {
                "available" -> MaterialTheme.colorScheme.primary
                "reserved" -> Color(0xFFF6C23E)  // Amber
                "sold" -> Color(0xFF4CAF50)      // Green
                "under maintenance" -> Color(0xFFDC3545)  // Red
                else -> MaterialTheme.colorScheme.tertiary
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(statusColor)
                )

                Text(
                    text = unit.status,
                    style = MaterialTheme.typography.titleMedium,
                    color = statusColor,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Divider(modifier = Modifier.padding(horizontal = 16.dp))

            // Location information
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Location Information",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    DetailRow(
                        icon = Icons.Outlined.Apartment,
                        label = "Development",
                        value = unit.developmentName
                    )

                    DetailRow(
                        icon = Icons.Outlined.Business,
                        label = "Building",
                        value = unit.buildingName
                    )
                }
            }

            // Unit details
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp), colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Unit Information",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    DetailRow(
                        icon = Icons.Outlined.Home, label = "Unit Type", value = unit.unitType
                    )

                    DetailRow(
                        icon = Icons.Outlined.AttachMoney,
                        label = "Price",
                        value = currencyFormatter.format(unit.price)
                    )

                    DetailRow(
                        icon = Icons.Outlined.CalendarMonth,
                        label = "Created Date",
                        value = unit.createdTime
                    )

                    DetailRow(
                        icon = Icons.Outlined.QrCode, label = "Unit ID", value = "#${unit.id}"
                    )
                }
            }


            if (unitWithDetails.deals.isNotEmpty()) {
                Text(
                    "Deals",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )
                DealOverviewCard(unitWithDetails.deals[0].deal)

                Text(
                    "Payments",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(
                        horizontal = 32.dp
                    )
                )

                unitWithDetails.deals[0].payments.forEach {
                    PaymentCard(it)
                }
            }

            // Spacer at the bottom for better scrolling
            Spacer(modifier = Modifier.height(24.dp))


        }

    }
}

@Composable
fun DetailRow(
    icon: ImageVector, label: String, value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}