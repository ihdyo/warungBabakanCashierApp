package com.babakan.cashier.data.repository.product

import com.babakan.cashier.utils.constant.RemoteData
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.owner.model.ProductModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class ProductRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val productCollection = firestore.collection(RemoteData.COLLECTION_PRODUCTS)

    suspend fun getProducts(): UiState<List<ProductModel>> {
        return try {
            val snapshot = productCollection
                .orderBy(RemoteData.FIELD_NAME)
                .get()
                .await()

            val product = snapshot.documents.mapNotNull { doc ->
                if (doc.exists()) ProductModel.fromDocumentSnapshot(doc) else null
            }

            UiState.Success(product)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun getProductById(
        productId: String
    ): UiState<ProductModel> {
        return try {
            val snapshot = productCollection
                .document(productId)
                .get()
                .await()

            if (snapshot.exists()) {
                val product = ProductModel.fromDocumentSnapshot(snapshot)
                UiState.Success(product)
            } else {
                UiState.Error("Produk tidak ditemukan", "Produk dengan ID $productId tidak ditemukan")
            }
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun createProduct(
        productData: ProductModel
    ): UiState<Unit> {
        return try {
            val documentRef = productCollection.add(productData.toJson()).await()

            val updatedProductData = productData.copy(id = documentRef.id)

            productCollection
                .document(documentRef.id)
                .set(updatedProductData.toJson())
                .await()

            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun updateProduct(
        productId: String,
        productData: ProductModel
    ): UiState<Unit> {
        return try {
            productCollection
                .document(productId)
                .set(productData.toJson(), SetOptions.merge())
                .await()

            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun deleteProduct(
        productId: String
    ): UiState<Unit> {
        return try {
            productCollection
                .document(productId)
                .delete()
                .await()

            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun searchProductsByName(
        query: String
    ): UiState<List<ProductModel>> {
        return try {
            val snapshot = productCollection
                .whereGreaterThanOrEqualTo(RemoteData.FIELD_NAME, query)
                .whereLessThanOrEqualTo(RemoteData.FIELD_NAME, query + "\uf8ff")
                .get()
                .await()

            val products = snapshot.documents.mapNotNull { doc ->
                if (doc.exists()) ProductModel.fromDocumentSnapshot(doc) else null
            }

            UiState.Success(products)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun searchProductsByCategoryId(
        categoryId: String,
        isShowAll: Boolean
    ): UiState<List<ProductModel>> {
        return try {
            val query = if (isShowAll) {
                productCollection
            } else {
                productCollection.whereEqualTo(RemoteData.FIELD_CATEGORY_ID, categoryId)
            }

            val snapshot = query.get().await()

            val products = snapshot.documents.mapNotNull { doc ->
                if (doc.exists()) ProductModel.fromDocumentSnapshot(doc) else null
            }

            UiState.Success(products)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

}