package com.babakan.cashier.data.repository.auth

import com.babakan.cashier.presentation.authentication.model.UserModel
import com.babakan.cashier.utils.constant.RemoteData
import com.babakan.cashier.data.state.UiState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val userCollection = firestore.collection(RemoteData.COLLECTION_USERS)

    suspend fun getUserDocument(
        userId: String
    ): UiState<UserModel> {
        return try {
            val snapshot = userCollection
                .document(userId)
                .get()
                .await()

            if (snapshot.exists()) {
                val userModel = UserModel.fromDocumentSnapshot(snapshot)
                UiState.Success(userModel)
            } else {
                UiState.Error("Pengguna tidak ditemukan", "Pengguna dengan ID $userId tidak ditemukan")
            }
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun setUserDocument(
        userId: String,
        userModel: UserModel
    ): UiState<String> {
        return try {
            userCollection
                .document(userId)
                .set(userModel.toJson(), SetOptions.merge())
                .await()
            UiState.Success("Data pengguna berhasil disimpan")
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun updateUserDocument(
        userId: String,
        fieldName: String,
        newValue: Any
    ): UiState<String> {
        return try {
            val updatedField = mapOf(fieldName to newValue)

            userCollection
                .document(userId)
                .update(updatedField)
                .await()

            UiState.Success("'$fieldName' berhasil diperbarui")
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun deleteUserDocument(
        userId: String
    ): UiState<String> {
        return try {
            userCollection
                .document(userId)
                .delete()
                .await()
            UiState.Success("Data pengguna berhasil dihapus")
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    fun observeIsActiveField(
        userId: String
    ): Flow<Boolean> = callbackFlow {
        val listenerRegistration: ListenerRegistration = userCollection
            .document(userId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    val isActive = snapshot.getBoolean(RemoteData.FIELD_IS_ACTIVE) ?: false
                    trySend(isActive)
                }
            }
        awaitClose { listenerRegistration.remove() }
    }
}