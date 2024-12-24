package com.babakan.cashier.data.repository.auth

import com.babakan.cashier.data.repository.user.UserRepository
import com.babakan.cashier.presentation.authentication.model.UserModel
import com.babakan.cashier.data.state.UiState
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val userRepository: UserRepository = UserRepository(),
) {

    fun isUserSignedIn(): Boolean {
        return auth.currentUser != null
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

    suspend fun signUpAuth(
        name: String,
        username: String,
        email: String,
        password: String
    ): UiState<String> {
        return try {
            val usernameResult = userRepository.searchUsersByUsername(username)
            if (usernameResult is UiState.Success && usernameResult.data.isNotEmpty()) {
                return UiState.Error("Registrasi Gagal", "Nama pengguna sudah digunakan.")
            }

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
                isActive = false
            )
            val saveResult = userRepository.createUser(userModel)
            if (saveResult is UiState.Success) {
                UiState.Success("Registrasi Berhasil!")
            } else {
                UiState.Error("Registrasi Gagal", "Gagal menyimpan data pengguna.")
            }
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun loginAuth(
        email: String,
        password: String
    ): UiState<Pair<String, String>> {
        return try {

            val userResult = suspendCoroutine { continuation ->
                userRepository.getUserByEmail(email) { result ->
                    continuation.resume(result)
                }
            }

            if (userResult is UiState.Success) {
                val isActive = userResult.data

                if (isActive) {
                    val authResult = auth.signInWithEmailAndPassword(email, password).await()
                    val user = authResult.user
                    if (user != null) {
                        val userId = user.uid
                        UiState.Success(Pair(userId, "Berhasil Masuk!"))
                    } else {
                        UiState.Error("Gagal Masuk", "Autentikasi gagal.")
                    }
                } else {
                    UiState.Error("Gagal Masuk", "Akun tidak aktif.")
                }
            } else {
                val errorMessage = if (userResult is UiState.Error) userResult.message else "Email tidak ditemukan."
                UiState.Error("Gagal Masuk", errorMessage)
            }
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    fun signOutAuth(): UiState<String> {
        return try {
            auth.signOut()
            UiState.Success("Berhasil Logout!")
        } catch (e: Exception) {
            UiState.Error("Gagal Logout!", e.message.toString())
        }
    }
}