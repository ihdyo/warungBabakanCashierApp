package com.babakan.cashier.data.repository.transaction

import com.babakan.cashier.data.repository.user.UserRepository
import com.babakan.cashier.utils.constant.RemoteData
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.owner.model.TransactionModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class TransactionRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val userRepository: UserRepository = UserRepository()
) {
    private val transactionCollection = firestore.collection(RemoteData.COLLECTION_TRANSACTIONS)

    suspend fun getTransactions(): UiState<List<TransactionModel>> {
        return try {
            val snapshot = transactionCollection
                .get()
                .await()

            val transaction = snapshot.documents.mapNotNull { doc ->
                if (doc.exists()) TransactionModel.fromDocumentSnapshot(doc) else null
            }

            UiState.Success(transaction)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun getTransactionById(
        transactionId: String
    ): UiState<TransactionModel> {
        return try {
            val snapshot = transactionCollection
                .document(transactionId)
                .get()
                .await()

            if (snapshot.exists()) {
                val transaction = TransactionModel.fromDocumentSnapshot(snapshot)
                UiState.Success(transaction)
            } else {
                UiState.Error("Transaksi tidak ditemukan", "Transaksi dengan ID $transactionId tidak ditemukan")
            }
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun setTransactionById(
        transactionId: String,
        transactionModel: TransactionModel
    ): UiState<Unit> {
        return try {
            transactionCollection
                .document(transactionId)
                .set(transactionModel.toJson(), SetOptions.merge())
                .await()

            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun updateTransactionById(
        transactionId: String,
        fieldName: String,
        newValue: Any
    ): UiState<Unit> {
        return try {
            val updatedField = mapOf(fieldName to newValue)

            transactionCollection
                .document(transactionId)
                .update(updatedField)
                .await()

            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun deleteTransactionById(
        transactionId: String
    ): UiState<Unit> {
        return try {
            transactionCollection
                .document(transactionId)
                .delete()
                .await()

            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun searchTransactionsByTransactionId(
        transactionId: String
    ): UiState<List<TransactionModel>> {
        return try {
            val snapshot = transactionCollection
                .whereEqualTo(RemoteData.FIELD_TRANSACTION_ID, transactionId)
                .get()
                .await()

            val transactions = snapshot.documents.mapNotNull { doc ->
                if (doc.exists()) TransactionModel.fromDocumentSnapshot(doc) else null
            }

            UiState.Success(transactions)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun searchTransactionsByUserName(
        query: String
    ): UiState<List<TransactionModel>> {
        return try {
            val userResult = userRepository.searchUsersByName(query)

            if (userResult is UiState.Error) {
                return UiState.Error("Terjadi kesalahan", userResult.message)
            }

            val userIds = (userResult as UiState.Success).data.map { it.id }

            if (userIds.isEmpty()) {
                return UiState.Error("Pengguna tidak ditemukan", "Tidak ada pengguna yang sesuai dengan nama $query")
            }

            val snapshot = transactionCollection
                .whereIn(RemoteData.FIELD_USER_ID, userIds)
                .get()
                .await()

            val transactions = snapshot.documents.mapNotNull { doc ->
                if (doc.exists()) TransactionModel.fromDocumentSnapshot(doc) else null
            }

            UiState.Success(transactions)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun searchTransactionsByDateRange(
        startDate: Timestamp,
        endDate: Timestamp
    ): UiState<List<TransactionModel>> {
        return try {
            val snapshot = transactionCollection
                .whereGreaterThanOrEqualTo(RemoteData.FIELD_CREATED_AT, startDate)
                .whereLessThanOrEqualTo(RemoteData.FIELD_CREATED_AT, endDate)
                .get()
                .await()

            val transactions = snapshot.documents.mapNotNull { doc ->
                if (doc.exists()) TransactionModel.fromDocumentSnapshot(doc) else null
            }

            UiState.Success(transactions)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

}