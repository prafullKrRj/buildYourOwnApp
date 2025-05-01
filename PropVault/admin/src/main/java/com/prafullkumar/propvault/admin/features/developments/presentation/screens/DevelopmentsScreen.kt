package com.prafullkumar.propvault.admin.features.developments.presentation.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prafullkumar.common.models.Development
import com.prafullkumar.common.presentation.DevelopmentCard
import com.prafullkumar.common.presentation.ErrorScreen
import com.prafullkumar.common.presentation.LoadingScreen
import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.admin.app.navigation.AdminRoutes
import com.prafullkumar.propvault.admin.features.developments.presentation.viewmodels.DevelopmentViewModel

@Composable
fun DevelopmentsScreen(
    viewModel: DevelopmentViewModel, navController: NavController
) {
    val developments by viewModel.developments.collectAsState()
    when (developments) {
        is Resource.Error -> {
            ErrorScreen(onRetry = viewModel::getDevelopments)
        }

        Resource.Loading -> {
            LoadingScreen()
        }

        is Resource.Success<List<Development>> -> {
            DevelopmentsUI(
                developments = (developments as Resource.Success<List<Development>>).data,
                navController = navController
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DevelopmentsUI(
    developments: List<Development>, navController: NavController
) {
    var searchQuery by remember { mutableStateOf("") }
    var showFilterDialog by remember { mutableStateOf(false) }


    val filteredDevelopments = remember(developments, searchQuery) {
        if (searchQuery.isBlank()) {
            developments
        } else {
            developments.filter {
                it.name.contains(
                    searchQuery, ignoreCase = true
                ) || it.category.contains(searchQuery, ignoreCase = true) || it.city.contains(
                    searchQuery, ignoreCase = true
                )
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Development Projects"
                )
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(AdminRoutes.AddDevelopment)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Development")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(top = paddingValues.calculateTopPadding())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Search developments...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = { showFilterDialog = true },
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary)
                        .size(46.dp)
                ) {
                    Icon(
                        Icons.Rounded.FilterList,
                        contentDescription = "Filter",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Development list
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(12.dp)
            ) {
                items(filteredDevelopments) { development ->
                    DevelopmentCard(
                        development = development, onClick = {
                            navController.navigate(
                                AdminRoutes.DevelopmentBuildingsScreen(
                                    development.id, development.name
                                )
                            )
                        }
                    )
                }
            }
        }
    }

    if (showFilterDialog) {
        FilterDialog(onDismiss = { showFilterDialog = false })
    }
}

@Composable
fun FilterDialog(onDismiss: () -> Unit) {
    AlertDialog(onDismissRequest = onDismiss, title = { Text("Filter Developments") }, text = {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Filter options would go here
            Text("Filter options UI would go here")
        }
    }, confirmButton = {
        Button(onClick = onDismiss) {
            Text("Apply")
        }
    }, dismissButton = {
        TextButton(onClick = onDismiss) {
            Text("Cancel")
        }
    })
}