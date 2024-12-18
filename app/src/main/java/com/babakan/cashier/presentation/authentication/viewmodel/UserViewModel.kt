package com.babakan.cashier.presentation.authentication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babakan.cashier.presentation.authentication.model.UserModel
import com.babakan.cashier.utils.constant.RemoteData
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UserViewModel : ViewModel() {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun saveUserData(
        userId: String,
        name: String,
        username: String,
        email: String
    ) {
        val userData = UserModel(
            id = userId,
            createdAt = Timestamp.now(),
            updateAt = Timestamp.now(),
            username = username,
            name = name,
            email = email,
            isOwner = false,
            isActive = true
        )

        viewModelScope.launch {
            try {
                firestore.collection(RemoteData.COLLECTION_USERS)
                    .document(userId)
                    .set(userData.toJson())
                    .await()
            } catch (e: Exception) {
                throw e
            }
        }
    }
}
