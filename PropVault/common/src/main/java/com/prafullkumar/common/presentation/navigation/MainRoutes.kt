package com.prafullkumar.common.presentation.navigation


import kotlinx.serialization.Serializable

sealed interface MainRoutes {

    @Serializable
    data object OnBoarding : MainRoutes

    @Serializable
    data object App : MainRoutes
}