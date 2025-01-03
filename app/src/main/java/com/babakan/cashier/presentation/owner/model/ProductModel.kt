package com.babakan.cashier.presentation.owner.model

import com.babakan.cashier.utils.constant.RemoteData
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot

data class ProductModel(
    val id: String = "",
    val createdAt: Timestamp = Timestamp.now(),
    var updateAt: Timestamp = Timestamp.now(),
    var name: String = "",
    var categoryId: String = "",
    var imageUrl: String = "",
    var price: Double = 0.0,
) {

    fun toJson(): Map<String, Any> {
        return mapOf(
            RemoteData.FIELD_ID to id,
            RemoteData.FIELD_CREATED_AT to createdAt,
            RemoteData.FIELD_UPDATE_AT to updateAt,
            RemoteData.FIELD_NAME to name,
            RemoteData.FIELD_CATEGORY_ID to categoryId,
            RemoteData.FIELD_IMAGE_URL to imageUrl,
            RemoteData.FIELD_PRICE to price,
        )
    }

    companion object {
        fun fromDocumentSnapshot(documentSnapshot: DocumentSnapshot): ProductModel {
            val id = documentSnapshot.getString(RemoteData.FIELD_ID) ?: ""
            val createdAt = documentSnapshot.getTimestamp(RemoteData.FIELD_CREATED_AT) ?: Timestamp.now()
            val updateAt = documentSnapshot.getTimestamp(RemoteData.FIELD_UPDATE_AT) ?: Timestamp.now()
            val name = documentSnapshot.getString(RemoteData.FIELD_NAME) ?: ""
            val categoryId = documentSnapshot.getString(RemoteData.FIELD_CATEGORY_ID) ?: ""
            val imageUrl = documentSnapshot.getString(RemoteData.FIELD_IMAGE_URL) ?: ""
            val price = documentSnapshot.getDouble(RemoteData.FIELD_PRICE) ?: 0.0

            return ProductModel(
                id = id,
                createdAt = createdAt,
                updateAt = updateAt,
                name = name,
                categoryId = categoryId,
                imageUrl = imageUrl,
                price = price
            )
        }
    }

}