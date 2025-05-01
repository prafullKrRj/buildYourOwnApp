package com.prafullkumar.propvault.admin.features.developments.di

import com.prafullkumar.propvault.admin.features.developments.data.DevelopmentRepositoryImpl
import com.prafullkumar.propvault.admin.features.developments.domain.DevelopmentsRepository
import com.prafullkumar.propvault.admin.features.developments.presentation.viewmodels.AddBuildingViewModel
import com.prafullkumar.propvault.admin.features.developments.presentation.viewmodels.AddDevelopmentViewModel
import com.prafullkumar.propvault.admin.features.developments.presentation.viewmodels.BuildingUnitViewModel
import com.prafullkumar.propvault.admin.features.developments.presentation.viewmodels.DevelopmentBuildingsViewModel
import com.prafullkumar.propvault.admin.features.developments.presentation.viewmodels.DevelopmentViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val developmentModule = module {
    viewModel {
        DevelopmentViewModel()
    }
    viewModel {
        AddBuildingViewModel(get(), get())
    }
    viewModel { AddDevelopmentViewModel() }
    viewModel { BuildingUnitViewModel(get()) }
    viewModel { DevelopmentBuildingsViewModel(get(), get()) }
    single<DevelopmentsRepository> {
        DevelopmentRepositoryImpl()
    }
}