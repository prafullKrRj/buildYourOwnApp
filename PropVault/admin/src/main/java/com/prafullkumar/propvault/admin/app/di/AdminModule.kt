package com.prafullkumar.propvault.admin.app.di

import com.prafullkumar.propvault.admin.features.deals.DealViewModel
import com.prafullkumar.propvault.admin.features.deals.DealsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val adminModule = module {
    viewModel { DealsViewModel() }
    viewModel { DealViewModel(get()) }
}