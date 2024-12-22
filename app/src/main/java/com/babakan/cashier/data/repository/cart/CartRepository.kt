package com.babakan.cashier.data.repository.cart

import com.babakan.cashier.utils.constant.RemoteData
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.owner.model.ProductOutModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CartRepository(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val currentUserId = auth.currentUser?.uid.toString()
    private val cartCollection = firestore
        .collection(RemoteData.COLLECTION_USERS)
        .document(currentUserId)
        .collection(RemoteData.COLLECTION_CART)

    suspend fun getCart(): UiState<List<ProductOutModel>> {
        return try {
            val snapshot = cartCollection
                .get()
                .await()

            val cart = snapshot.documents.mapNotNull { doc ->
                if (doc.exists()) ProductOutModel.fromDocumentSnapshot(doc) else null
            }

            UiState.Success(cart)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun createCart(
        listCartData: List<ProductOutModel>
    ): UiState<Unit> {
        return try {
            val batch = cartCollection.firestore.batch()

            listCartData.forEach { cartItem ->
                val existingDocQuery = cartCollection
                    .whereEqualTo(RemoteData.FIELD_PRODUCT_ID, cartItem.productId)
                    .limit(1)

                val existingDocSnapshot = existingDocQuery.get().await()

                val docRef = cartCollection.document(cartItem.productId)

                if (existingDocSnapshot.isEmpty) {
                    batch.set(docRef, cartItem.toJson())
                } else {
                    val existingDoc = existingDocSnapshot.documents.first()
                    val updatedQuantity = (existingDoc.getLong(RemoteData.FIELD_QUANTITY) ?: 0) + cartItem.quantity
                    batch.update(docRef, RemoteData.FIELD_QUANTITY, updatedQuantity)
                }
            }

            batch.commit().await()

            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun setPrice(
        productId: String,
        price: Double
    ): UiState<Unit> {
        return try {
            val docRef = cartCollection.document(productId)
            docRef.update(RemoteData.FIELD_PRICE, price)
                .await()

            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun addQuantity(
        productId: String
    ): UiState<Unit> {
        return try {
            val docRef = cartCollection.document(productId)
            val existingDocSnapshot = docRef
                .get()
                .await()

            val currentQuantity = existingDocSnapshot.getLong(RemoteData.FIELD_QUANTITY) ?: 0
            val newQuantity = currentQuantity + 1
            docRef.update(RemoteData.FIELD_QUANTITY, newQuantity)
                .await()

            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun subtractQuantity(
        productId: String
    ): UiState<Unit> {
        return try {
            val docRef = cartCollection.document(productId)
            val existingDocSnapshot = docRef
                .get()
                .await()

            val currentQuantity = existingDocSnapshot.getLong(RemoteData.FIELD_QUANTITY) ?: 0
            val newQuantity = currentQuantity - 1

            if (newQuantity > 0) {
                docRef
                    .update(RemoteData.FIELD_QUANTITY, newQuantity)
                    .await()
            } else {
                docRef
                    .delete()
                    .await()
            }

            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun clearCart(): UiState<Unit> {
        return try {
            cartCollection.get().await().forEach { document ->
                document.reference.delete().await()
            }

            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

}