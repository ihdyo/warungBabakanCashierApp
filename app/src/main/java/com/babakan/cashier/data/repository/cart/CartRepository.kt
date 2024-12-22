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
        cartData: ProductOutModel
    ): UiState<Unit> {
        return try {
            cartCollection
                .add(cartData.toJson())
                .await()

            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun updateCartByProductId(
        productId: String,
        fieldName: String,
        newValue: Any
    ): UiState<Unit> {
        return try {
            val updatedField = mapOf(fieldName to newValue)

            cartCollection
                .document(productId)
                .update(updatedField)
                .await()

            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun deleteCartByProductId(
        productId: String
    ): UiState<Unit> {
        return try {
            cartCollection
                .document(productId)
                .delete()
                .await()

            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

}