package com.babakan.cashier.data.repository.auth

import com.babakan.cashier.data.repository.user.UserRepository
import com.babakan.cashier.presentation.authentication.model.UserModel
import com.babakan.cashier.data.state.UiState
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val userRepository: UserRepository = UserRepository()
) {

    suspend fun signUpUser(
        name: String,
        username: String,
        email: String,
        password: String
    ): UiState<String> {
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val userId = authResult.user?.uid ?: return UiState.Error("Registrasi Gagal", "ID pengguna tidak ditemukan.")

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
            val saveResult = userRepository.setUserById(userId, userModel)
            if (saveResult is UiState.Success) {
                UiState.Success("Registrasi Berhasil!")
            } else {
                UiState.Error("Registrasi Gagal", "Gagal menyimpan data pengguna.")
            }
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun loginUser(email: String, password: String): UiState<String> {
        return try {
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            if (authResult.user != null) {
                UiState.Success("Berhasil Masuk!")
            } else {
                UiState.Error("Gagal Masuk", "Email atau kata sandi tidak sesuai.")
            }
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    fun signOutUser(): UiState<String> {
        return try {
            auth.signOut()
            UiState.Success("Berhasil Logout!")
        } catch (e: Exception) {
            UiState.Error("Gagal Logout!", e.message.toString())
        }
    }

    suspend fun getCurrentUser(): UiState<UserModel> {
        return try {
            val userId = auth.currentUser?.uid
            if (userId != null) {
                val userState = userRepository.getUserById(userId)
                if (userState is UiState.Success) {
                    UiState.Success(userState.data)
                } else {
                    UiState.Error("Pengguna tidak ditemukan", "Tidak ada data untuk pengguna.")
                }
            } else {
                UiState.Error("Tidak ada pengguna yang masuk", "Pengguna tidak terautentikasi.")
            }
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

}