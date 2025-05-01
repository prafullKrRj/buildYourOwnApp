package com.prafullkumar.common.navigation


import kotlinx.serialization.Serializable

sealed interface MainRoutes {

    @Serializable
    data object OnBoarding : MainRoutes

    @Serializable
    data object Admin : MainRoutes

    @Serializable
    data object Customer: MainRoutes
}