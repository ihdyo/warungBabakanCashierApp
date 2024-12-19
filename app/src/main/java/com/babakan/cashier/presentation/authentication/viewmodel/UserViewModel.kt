package com.babakan.cashier.presentation.authentication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.authentication.model.UserModel
import com.babakan.cashier.utils.constant.RemoteData
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class UserViewModel : ViewModel() {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _userDocument = MutableStateFlow<UserModel?>(null)
    val userDocument: StateFlow<UserModel?> = _userDocument

    suspend fun fetchUser(
        userId: String
    ): UiState<UserModel> {
        return try {
            val snapshot = firestore
                .collection(RemoteData.COLLECTION_USERS)
                .document(userId)
                .get()
                .await()

            if (snapshot.exists()) {
                UiState.Success(UserModel.fromDocumentSnapshot(snapshot))
            } else {
                UiState.Error("Tidak ditemukan", "Tidak ada data untuk pengguna dengan ID: $userId")
            }
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    fun createUser(
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
