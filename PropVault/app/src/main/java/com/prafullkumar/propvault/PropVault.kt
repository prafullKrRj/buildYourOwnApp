package com.prafullkumar.propvault

import android.app.Application
import com.prafullkumar.propvault.admin.app.di.adminModules
import com.prafullkumar.propvault.customer.app.di.customerModule
import com.prafullkumar.propvault.onBoarding.di.onBoardingModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class PropVault: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PropVault)
            androidLogger()
            modules(adminModules + onBoardingModule + customerModule)
        }
    }
}