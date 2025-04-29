package com.prafullkumar.propvault.onBoarding.di

import com.prafullkumar.propvault.onBoarding.data.OnBoardingRepositoryImpl
import com.prafullkumar.propvault.onBoarding.domain.OnBoardingRepository
import com.prafullkumar.propvault.onBoarding.ui.OnBoardingViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val onBoardingModule = module {
    single<OnBoardingRepository> {
        OnBoardingRepositoryImpl()
    }
    viewModel { OnBoardingViewModel(get()) }
}