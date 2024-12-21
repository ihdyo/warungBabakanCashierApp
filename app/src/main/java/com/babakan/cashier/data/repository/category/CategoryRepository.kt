package com.babakan.cashier.data.repository.category

import com.babakan.cashier.utils.constant.RemoteData
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.owner.model.CategoryModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CategoryRepository(
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    private val categoryCollection = firestore.collection(RemoteData.COLLECTION_CATEGORIES)

    suspend fun getCategories(): UiState<List<CategoryModel>> {
        return try {
            val snapshot = categoryCollection
                .get()
                .await()

            val category = snapshot.documents.mapNotNull { doc ->
                if (doc.exists()) CategoryModel.fromDocumentSnapshot(doc) else null
            }

            UiState.Success(category)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun getCategoryById(
        categoryId: String
    ): UiState<CategoryModel> {
        return try {
            val snapshot = categoryCollection
                .document(categoryId)
                .get()
                .await()

            if (snapshot.exists()) {
                val category = CategoryModel.fromDocumentSnapshot(snapshot)
                UiState.Success(category)
            } else {
                UiState.Error("Kategori tidak ditemukan", "Kategori dengan ID $categoryId tidak ditemukan")
            }
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun createCategory(
        categoryData: CategoryModel
    ): UiState<Unit> {
        return try {
            categoryCollection
                .add(categoryData.toJson())
                .await()

            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun updateCategoryById(
        categoryId: String,
        fieldName: String,
        newValue: Any
    ): UiState<Unit> {
        return try {
            val updatedField = mapOf(fieldName to newValue)

            categoryCollection
                .document(categoryId)
                .update(updatedField)
                .await()

            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun deleteCategoryById(
        categoryId: String
    ): UiState<Unit> {
        return try {
            categoryCollection
                .document(categoryId)
                .delete()
                .await()

            UiState.Success(Unit)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

    suspend fun searchCategoriesByName(
        query: String
    ): UiState<List<CategoryModel>> {
        return try {
            val snapshot = categoryCollection
                .whereGreaterThanOrEqualTo(RemoteData.FIELD_NAME, query)
                .whereLessThanOrEqualTo(RemoteData.FIELD_NAME, query + "\uf8ff")
                .get()
                .await()

            val categories = snapshot.documents.mapNotNull { doc ->
                if (doc.exists()) CategoryModel.fromDocumentSnapshot(doc) else null
            }

            UiState.Success(categories)
        } catch (e: Exception) {
            UiState.Error("Terjadi kesalahan", e.message.toString())
        }
    }

}