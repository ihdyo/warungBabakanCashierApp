package com.babakan.cashier.data.repository.auth

import com.babakan.cashier.presentation.authentication.model.UserModel
import com.babakan.cashier.utils.constant.RemoteData
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class AuthRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val userCollection = firestore.collection(RemoteData.COLLECTION_USERS)

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

            userCollection.document(userId).set(userModel.toJson(), SetOptions.merge()).await()
            true
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
