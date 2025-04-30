package com.prafullkumar.propvault.onBoarding.di

import com.prafullkumar.propvault.onBoarding.ui.OnBoardingViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val onBoardingModule = module {

    viewModel { OnBoardingViewModel() }
}