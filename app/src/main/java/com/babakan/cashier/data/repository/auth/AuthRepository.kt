package com.babakan.cashier.data.repository.auth

import com.babakan.cashier.presentation.authentication.model.UserModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val userRepository: UserRepository = UserRepository()
) {

    suspend fun signUp(
        name: String,
        username: String,
        email: String,
        password: String
    ): Boolean {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = authResult.user?.uid ?: return false

            val userModel = UserModel(
                id = userId,
                createdAt = Timestamp.now(),
                updateAt = Timestamp.now(),
                username = username,
                name = name,
                email = email,
                isOwner = false,
                isActive = true
            )

            val saveResult = userRepository.setUserDocument(userId, userModel)
            saveResult is com.babakan.cashier.data.state.UiState.Success
        } catch (e: Exception) {
            false
        }
    }

    suspend fun login(
        email: String,
        password: String
    ): Boolean {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            authResult.user != null
        } catch (e: Exception) {
            false
        }
    }
}

