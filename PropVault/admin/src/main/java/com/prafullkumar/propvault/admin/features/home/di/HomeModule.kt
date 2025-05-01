package com.prafullkumar.propvault.admin.features.home.di

import com.prafullkumar.propvault.admin.features.home.data.HomeRepositoryImpl
import com.prafullkumar.propvault.admin.features.home.domain.repository.HomeRepository
import com.prafullkumar.propvault.admin.features.home.presentation.viewmodels.HomeViewModel
import com.prafullkumar.propvault.admin.features.home.presentation.viewmodels.PaymentViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel { HomeViewModel() }
    viewModel {
        PaymentViewModel()
    }
    single<HomeRepository> {
        HomeRepositoryImpl()
    }
}