package com.prafullkumar.propvault.onBoarding.data

import com.google.firebase.auth.FirebaseAuth
import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.onBoarding.domain.OnBoardingRepository
import com.prafullkumar.propvault.onBoarding.ui.UserRole
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class OnBoardingRepositoryImpl : OnBoardingRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()
    override fun loginUser(
        userName: String,
        password: String,
        role: UserRole
    ): Flow<Resource<Boolean>> {
        return flow {
            try {
                val email = "${userName}@propvault-${if (role == UserRole.ADMIN) "admin" else "cust"}.com"
                val response = firebaseAuth.signInWithEmailAndPassword(email, password).isSuccessful
                if (response) {
                    emit(Resource.Success(true))
                } else {
                    emit(Resource.Error("Login failed"))
                }
            } catch (e: Exception) {
                emit(Resource.Error("Login failed: ${e.message}"))
            }
        }
    }

    override fun signUpUser(
        userName: String,
        password: String,
        role: UserRole
    ): Flow<Resource<Boolean>> {
        return flow {
            try {
                val email = "${userName}@propvault-${if (role == UserRole.ADMIN) "admin" else "cust"}.com"
                val response = firebaseAuth.createUserWithEmailAndPassword(email, password).isSuccessful
                if (response) {
                    emit(Resource.Success(true))
                } else {
                    emit(Resource.Error("Sign up failed"))
                }
            } catch (e: Exception) {
                emit(Resource.Error("Sign up failed: ${e.message}"))
            }
        }
    }

}