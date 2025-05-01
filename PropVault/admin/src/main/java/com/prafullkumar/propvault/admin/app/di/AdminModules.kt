package com.prafullkumar.propvault.admin.app.di

import com.prafullkumar.propvault.admin.features.deals.di.dealsModule
import com.prafullkumar.propvault.admin.features.developments.di.developmentModule
import com.prafullkumar.propvault.admin.features.home.di.homeModule
import com.prafullkumar.propvault.admin.features.unit.di.unitModule

val adminModules = listOf(
    dealsModule,
    developmentModule, homeModule, unitModule
)