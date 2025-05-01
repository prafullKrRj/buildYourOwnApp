package com.prafullkumar.propvault.onBoarding.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.prafullkumar.propvault.onBoarding.ui.screens.UserRole
import kotlinx.coroutines.launch


enum class LoginOrSignUp {
    LOGIN,
    SIGNUP
}

/**
 * ViewModel for handling user authentication during onboarding process.
 * Manages login, signup, and role selection (Admin/Customer) functionality.
 */
class OnBoardingViewModel : ViewModel() {

    // Firebase Authentication instance for handling auth operations
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    // State to track authentication errors
    var errorState by mutableStateOf(false)

    // Navigation state to determine when to navigate to main app
    private val _navigateToApp = mutableStateOf(false)
    val navigateToApp: State<Boolean> get() = _navigateToApp

    // UI state for login/signup mode and user role selection
    var loginOrSignUp by mutableStateOf(LoginOrSignUp.LOGIN)
    var selectedRole by mutableStateOf(UserRole.CUSTOMER)

    /**
     * Handles user login with email and password.
     * Constructs email based on username and selected role (admin/customer).
     * @param username User's input username
     * @param password User's input password
     */
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

            }
        }
    }

    /**
     * Handles customer signup with email and password.
     * Creates new user account in Firebase Authentication.
     * @param username User's input username
     * @param password User's input password
     */
    fun signup(username: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(
            "$username@propvault-cust.com",
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