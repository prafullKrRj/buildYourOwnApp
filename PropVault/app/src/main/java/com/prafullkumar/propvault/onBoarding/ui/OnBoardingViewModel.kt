package com.prafullkumar.propvault.onBoarding.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.prafullkumar.common.utils.Resource
import com.prafullkumar.propvault.onBoarding.domain.OnBoardingRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


enum class LoginOrSignUp {
    LOGIN,
    SIGNUP
}

class OnBoardingViewModel(
    private val onBoardingRepository: OnBoardingRepository
) : ViewModel() {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    var errorState by mutableStateOf(false)

    private val _navigateToApp = mutableStateOf(false)
    val navigateToApp: State<Boolean> get() = _navigateToApp


    var loginOrSignUp by mutableStateOf(LoginOrSignUp.LOGIN)
    var selectedRole by mutableStateOf(UserRole.CUSTOMER)


    fun login(username: String, password: String) {
        viewModelScope.launch {
            if (loginOrSignUp == LoginOrSignUp.LOGIN) {
                onBoardingRepository.loginUser(username, password, selectedRole).collectLatest {
                    if (it is Resource.Success) {
                        errorState = false
                        _navigateToApp.value = true
                    } else if (it is Resource.Error) {
                        errorState = true
                    }
                }
            } else {
                onBoardingRepository.signUpUser(username, password, selectedRole).collectLatest {
                    if (it is Resource.Success) {
                        errorState = false
                        _navigateToApp.value = true
                    } else if (it is Resource.Error) {
                        errorState = true
                    }
                }
            }
        }
    }

}