package com.babakan.cashier.data.repository.productOut

import android.util.Log
import com.babakan.cashier.utils.constant.RemoteData
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.owner.model.ProductOutModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProductOutRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val transactionCollection = firestore.collection(RemoteData.COLLECTION_TRANSACTIONS)

    suspend fun getProductOut(
        transactionIds: List<String>
    ): UiState<List<Pair<String, List<ProductOutModel>>>> {
        return try {
            val productOutList = transactionIds.map { transactionId ->
                val snapshot = transactionCollection
                    .document(transactionId)
                    .collection(RemoteData.COLLECTION_PRODUCT_OUT)
                    .get()
                    .await()

                val productOut = snapshot.documents.mapNotNull { doc ->
                    if (doc.exists()) ProductOutModel.fromDocumentSnapshot(doc) else null
                }

                Pair(transactionId, productOut)
            }

            UiState.Success(productOutList)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun createProductOut(
        transactionId: String,
        productOutData: ProductOutModel
    ): UiState<Unit> {
        return try {
            transactionCollection
                .document(transactionId)
                .collection(RemoteData.COLLECTION_PRODUCT_OUT)
                .add(productOutData.toJson())
                .await()

            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun updateProductOutByProductId(
        transactionId: String,
        productId: String,
        fieldName: String,
        newValue: Any
    ): UiState<Unit> {
        return try {
            val updatedField = mapOf(fieldName to newValue)

            transactionCollection
                .document(transactionId)
                .collection(RemoteData.COLLECTION_PRODUCT_OUT)
                .document(productId)
                .update(updatedField)
                .await()

            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun deleteProductOutByProductId(
        transactionId: String,
        productId: String
    ): UiState<Unit> {
        return try {
            transactionCollection
                .document(transactionId)
                .collection(RemoteData.COLLECTION_PRODUCT_OUT)
                .document(productId)
                .delete()
                .await()

            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

}