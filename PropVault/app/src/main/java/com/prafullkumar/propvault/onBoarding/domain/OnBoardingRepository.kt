package com.prafullkumar.propvault.onBoarding.domain

import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.onBoarding.ui.UserRole
import kotlinx.coroutines.flow.Flow

interface OnBoardingRepository {

    fun loginUser(
        userName: String,
        password: String,
        role: UserRole,
    ): Flow<Resource<Boolean>>

    fun signUpUser(
        userName: String,
        password: String,
        role: UserRole,
    ): Flow<Resource<Boolean>>

}
