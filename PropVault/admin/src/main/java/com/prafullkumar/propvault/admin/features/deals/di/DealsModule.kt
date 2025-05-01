package com.prafullkumar.propvault.admin.features.deals.di

import com.prafullkumar.propvault.admin.features.deals.data.DealsRepositoryImpl
import com.prafullkumar.propvault.admin.features.deals.domain.repo.DealsRepository
import com.prafullkumar.propvault.admin.features.deals.presentation.viewmodels.DealViewModel
import com.prafullkumar.propvault.admin.features.deals.presentation.viewmodels.DealsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val dealsModule = module {
    viewModel { DealsViewModel() }
    viewModel { DealViewModel(get()) }
    single<DealsRepository> { DealsRepositoryImpl() }
}