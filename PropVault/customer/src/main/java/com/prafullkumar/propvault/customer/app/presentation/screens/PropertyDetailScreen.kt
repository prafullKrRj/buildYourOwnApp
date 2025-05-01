package com.prafullkumar.propvault.customer.app.presentation.screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apartment
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Pin
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafullkumar.common.models.PropertyUnit
import com.prafullkumar.common.presentation.ErrorScreen
import com.prafullkumar.common.presentation.LoadingScreen
import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.customer.app.presentation.viewmodels.UnitDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyDetailScreen(
    unitDetailViewModel: UnitDetailViewModel, navController: NavController
) {
    val propertyState by unitDetailViewModel.property.collectAsState()
    when (propertyState) {
        is Resource.Error -> {
            ErrorScreen(onRetry = unitDetailViewModel::getProperties)
        }

        Resource.Loading -> {
            LoadingScreen()
        }

        is Resource.Success<PropertyUnit> -> {
            val property = (propertyState as Resource.Success).data
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Property Details") },
                        navigationIcon = {
                            IconButton(onClick = { navController.navigateUp() }) {
                                Icon(Icons.Default.ArrowBack, "Back")
                            }
                        },
                    )
                }) { paddingValues ->
                property.let {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .background(MaterialTheme.colorScheme.background)
                            .padding(16.dp)
                    ) {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(4.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(20.dp)
                                    .fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                // Header Section
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = it.name,
                                        style = MaterialTheme.typography.headlineMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "₹${"%,.2f".format(it.price)}",
                                        style = MaterialTheme.typography.titleLarge,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Surface(
                                        shape = RoundedCornerShape(8.dp), color = when (it.status) {
                                            "Available" -> Color(0xFF4CAF50)
                                            "Sold" -> Color(0xFFE57373)
                                            else -> Color(0xFFFFB74D)
                                        }, modifier = Modifier.wrapContentWidth()
                                    ) {
                                        Text(
                                            text = it.status, modifier = Modifier.padding(
                                                horizontal = 12.dp, vertical = 4.dp
                                            ), color = Color.White
                                        )
                                    }
                                }

                                Divider()

                                // Details Section
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(12.dp)
                                ) {
                                    DetailRow(Icons.Default.Pin, "Unit Code", it.unitCode)
                                    DetailRow(
                                        Icons.Default.LocationCity,
                                        "Development",
                                        it.developmentName
                                    )
                                    DetailRow(Icons.Default.Apartment, "Building", it.buildingName)
                                    DetailRow(Icons.Default.Home, "Type", it.unitType)
                                    DetailRow(Icons.Default.Schedule, "Created", it.createdTime)
                                }
                                Divider()

                                // Contact Section
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "Contact Options",
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Button(
                                            onClick = { /* TODO: Implement call action */ },
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Icon(
                                                Icons.Default.Call,
                                                contentDescription = "Call",
                                                modifier = Modifier.size(ButtonDefaults.IconSize)
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text("Call")
                                        }
                                    }
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
private fun DetailRow(icon: ImageVector, label: String, value: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
    ) {
        Row(Modifier.weight(.6f), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = label, fontWeight = FontWeight.Medium, modifier = Modifier.width(100.dp)
            )
        }
        Text(
            text = value,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(.4f)
        )
    }
}