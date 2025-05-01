package com.prafullkumar.propvault.admin.features.unit.di

import com.prafullkumar.propvault.admin.features.unit.data.UnitRepositoryImpl
import com.prafullkumar.propvault.admin.features.unit.domain.UnitRepository
import com.prafullkumar.propvault.admin.features.unit.presentation.viewmodels.UnitEditViewModel
import com.prafullkumar.propvault.admin.features.unit.presentation.viewmodels.UnitViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val unitModule = module {
    viewModel { UnitEditViewModel(get()) }
    viewModel { UnitViewModel(get()) }
    single<UnitRepository> { UnitRepositoryImpl() }
}