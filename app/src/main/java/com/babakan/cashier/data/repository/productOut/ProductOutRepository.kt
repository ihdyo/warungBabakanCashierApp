package com.babakan.cashier.data.repository.productOut

import com.babakan.cashier.utils.constant.RemoteData
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.owner.model.ProductOutModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProductOutRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val productOutCollection = firestore.collection(RemoteData.COLLECTION_PRODUCT_OUT)

    suspend fun getProductOut(): UiState<List<ProductOutModel>> {
        return try {
            val snapshot = productOutCollection
                .get()
                .await()

            val productOut = snapshot.documents.mapNotNull { doc ->
                if (doc.exists()) ProductOutModel.fromDocumentSnapshot(doc) else null
            }

            UiState.Success(productOut)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun createProductOut(
        productOutData: ProductOutModel
    ): UiState<Unit> {
        return try {
            productOutCollection
                .add(productOutData.toJson())
                .await()

            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun updateProductOutByProductId(
        productId: String,
        fieldName: String,
        newValue: Any
    ): UiState<Unit> {
        return try {
            val updatedField = mapOf(fieldName to newValue)

            productOutCollection
                .document(productId)
                .update(updatedField)
                .await()

            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun deleteProductOutByProductId(
        productId: String
    ): UiState<Unit> {
        return try {
            productOutCollection
                .document(productId)
                .delete()
                .await()

            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

}