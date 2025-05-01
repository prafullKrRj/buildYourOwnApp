package com.prafullkumar.propvault.customer.app.di

import com.prafullkumar.propvault.customer.app.data.CustomerRepositoryImpl
import com.prafullkumar.propvault.customer.app.domain.CustomerRepository
import com.prafullkumar.propvault.customer.app.presentation.viewmodels.PropertyListViewModel
import com.prafullkumar.propvault.customer.app.presentation.viewmodels.UnitDetailViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val customerModule = module {
    single<CustomerRepository> { CustomerRepositoryImpl() }
    viewModel { PropertyListViewModel() }
    viewModel { UnitDetailViewModel(get()) }
}