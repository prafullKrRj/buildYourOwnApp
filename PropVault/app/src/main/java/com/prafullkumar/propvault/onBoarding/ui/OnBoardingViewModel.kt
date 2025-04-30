package com.prafullkumar.propvault.onBoarding.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch


enum class LoginOrSignUp {
    LOGIN,
    SIGNUP
}

class OnBoardingViewModel(

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
                firebaseAuth.signInWithEmailAndPassword(
                    "$username@propvault-${if (selectedRole == UserRole.ADMIN) "admin" else "cust"}.com",
                    password
                )
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            errorState = false
                            _navigateToApp.value = true
                        } else {
                            errorState = true
                        }
                    }
            } else {
                firebaseAuth.createUserWithEmailAndPassword(
                    "$username@propvault-${if (selectedRole == UserRole.ADMIN) "admin" else "cust"}.com",
                    password
                )
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            errorState = false
                            _navigateToApp.value = true
                        } else {
                            errorState = true
                        }
                    }
            }
        }
    }

}