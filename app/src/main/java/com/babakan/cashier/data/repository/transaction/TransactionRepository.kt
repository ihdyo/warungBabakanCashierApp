package com.babakan.cashier.data.repository.transaction

import com.babakan.cashier.data.repository.cart.CartRepository
import com.babakan.cashier.data.repository.productOut.ProductOutRepository
import com.babakan.cashier.data.repository.user.UserRepository
import com.babakan.cashier.utils.constant.RemoteData
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.owner.model.ProductOutModel
import com.babakan.cashier.presentation.owner.model.TransactionModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class TransactionRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance(),
    private val userRepository: UserRepository = UserRepository(),
    private val productOutRepository: ProductOutRepository = ProductOutRepository()
) {
    private val transactionCollection = firestore.collection(RemoteData.COLLECTION_TRANSACTIONS)

    suspend fun getTransactions(): UiState<List<TransactionModel>> {
        return try {
            val snapshot = transactionCollection
                .get()
                .await()

            val transactions = snapshot.documents.mapNotNull { doc ->
                if (doc.exists()) TransactionModel.fromDocumentSnapshot(doc) else null
            }

            val sortedTransactions = transactions.sortedByDescending { it.createdAt }

            UiState.Success(sortedTransactions)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun setTransactionById(
        transactionModel: TransactionModel,
        productOutData: List<ProductOutModel>
    ): UiState<Unit> {
        return try {
            val transactionId = transactionModel.transactionId

            transactionCollection
                .document(transactionId)
                .set(transactionModel.toJson(), SetOptions.merge())
                .await()

            val productOutResult = productOutRepository.createProductOut(transactionId, productOutData)
            if (productOutResult is UiState.Error) {
                return productOutResult
            }

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