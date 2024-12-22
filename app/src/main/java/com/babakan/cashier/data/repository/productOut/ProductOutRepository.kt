package com.babakan.cashier.data.repository.productOut

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
        transactionIds: String
    ): UiState<List<ProductOutModel>> {
        return try {
            val productOutCollection = transactionCollection
                .document(transactionIds)
                .collection(RemoteData.COLLECTION_PRODUCT_OUT)

            val productOutSnapshot = productOutCollection
                .get()
                .await()

            val productOutList = productOutSnapshot.documents.mapNotNull { doc ->
                if (doc.exists()) ProductOutModel.fromDocumentSnapshot(doc) else null
            }

            UiState.Success(productOutList)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun createProductOut(
        transactionId: String,
        listProductOutData: List<ProductOutModel>
    ): UiState<Unit> {
        return try {
            val productOutCollection = transactionCollection
                .document(transactionId)
                .collection(RemoteData.COLLECTION_PRODUCT_OUT)

            val batch = productOutCollection.firestore.batch()

            listProductOutData.forEach { product ->
                val docRef = productOutCollection.document(product.productId)
                batch.set(docRef, product.toJson())
            }

            batch.commit().await()

            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

}