package com.babakan.cashier.data.repository.user

import com.babakan.cashier.presentation.authentication.model.UserModel
import com.babakan.cashier.utils.constant.RemoteData
import com.babakan.cashier.data.state.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class UserRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val currentUserId = auth.currentUser?.uid.toString()
    private val userCollection = firestore.collection(RemoteData.COLLECTION_USERS)

    suspend fun getUsers(): UiState<List<UserModel>> {
        return try {
            val snapshot = userCollection
                .get()
                .await()

            val users = snapshot.documents.mapNotNull { doc ->
                if (doc.exists()) UserModel.fromDocumentSnapshot(doc) else null
            }

            val sortedUsers = users.sortedWith(compareByDescending<UserModel> { it.isOwner }.thenBy { it.name })

            UiState.Success(sortedUsers)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun getUserById(
        userId: String
    ): UiState<UserModel> {
        return try {
            val snapshot = userCollection
                .document(userId)
                .get()
                .await()

            if (snapshot.exists()) {
                val user = UserModel.fromDocumentSnapshot(snapshot)
                UiState.Success(user)
            } else {
                UiState.Error("Pengguna tidak ditemukan", "Pengguna dengan ID $userId tidak ditemukan")
            }
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun createUser(
        userData: UserModel
    ): UiState<Unit> {
        return try {
            val userId = userData.id

            userCollection
                .document(userId)
                .set(userData.toJson(), SetOptions.merge())
                .await()

            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun updateUser(
        userId: String,
        userData: UserModel
    ): UiState<Unit> {
        return try {
            userCollection
                .document(userId)
                .set(userData.toJson(), SetOptions.merge())
                .await()

            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    private suspend fun updateUserSingleField(
        userId: String,
        fieldName: String,
        newValue: Any
    ): UiState<Unit> {
        return try {
            val updatedField = mapOf(fieldName to newValue)

            userCollection
                .document(userId)
                .update(updatedField)
                .await()

            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun deleteUser(
        userId: String
    ): UiState<Unit> {
        return try {
            val updateState = updateUserSingleField(userId, RemoteData.FIELD_IS_ACTIVE, false)

            if (updateState is UiState.Success) {
                userCollection
                    .document(userId)
                    .delete()
                    .await()

                UiState.Success(Unit)
            } else {
                UiState.Error("Gagal menghapus pengguna", "Gagal menonaktifkan pengguna sebelum menghapus")
            }
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun searchUsersByName(
        query: String
    ): UiState<List<UserModel>> {
        return try {
            val snapshot = userCollection
                .whereGreaterThanOrEqualTo(RemoteData.FIELD_NAME, query)
                .whereLessThanOrEqualTo(RemoteData.FIELD_NAME, query + "\uf8ff")
                .get()
                .await()

            val users = snapshot.documents.mapNotNull { doc ->
                if (doc.exists()) UserModel.fromDocumentSnapshot(doc) else null
            }

            UiState.Success(users)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun searchUsersByUsername(
        query: String
    ): UiState<List<UserModel>> {
        return try {
            val snapshot = userCollection
                .whereEqualTo(RemoteData.FIELD_USERNAME, query)
                .get()
                .await()

            val users = snapshot.documents.mapNotNull { doc ->
                if (doc.exists()) UserModel.fromDocumentSnapshot(doc) else null
            }

            UiState.Success(users)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    fun listenCurrentUserIsActive(): Flow<Boolean> = callbackFlow {
        val listenerRegistration: ListenerRegistration = userCollection
            .document(currentUserId)
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