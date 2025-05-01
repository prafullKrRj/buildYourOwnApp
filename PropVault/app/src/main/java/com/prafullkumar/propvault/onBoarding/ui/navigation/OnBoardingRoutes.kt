package com.prafullkumar.propvault.onBoarding.ui.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

sealed interface OnBoardingRoutes : Parcelable {

    @Parcelize
    @Serializable
    data object Login : OnBoardingRoutes
}