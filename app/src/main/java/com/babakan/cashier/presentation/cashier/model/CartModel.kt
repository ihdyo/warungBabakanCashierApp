package com.babakan.cashier.presentation.cashier.model

import com.babakan.cashier.utils.constant.RemoteData
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot

data class CartModel(
    val id: String = "",
    val createdAt: Timestamp = Timestamp.now(),
    var updateAt: Timestamp = Timestamp.now(),
    var userId: String = "",
    var totalPrice: Double = 0.0,
) {

    fun toJson(): Map<String, Any> {
        return mapOf(
            RemoteData.FIELD_ID to id,
            RemoteData.FIELD_CREATED_AT to createdAt,
            RemoteData.FIELD_UPDATE_AT to updateAt,
            RemoteData.FIELD_USER_ID to userId,
            RemoteData.FIELD_TOTAL_PRICE to totalPrice
        )
    }

    companion object {
        fun fromDocumentSnapshot(documentSnapshot: DocumentSnapshot): CartModel {
            val id = documentSnapshot.getString(RemoteData.FIELD_ID) ?: ""
            val createdAt = documentSnapshot.getTimestamp(RemoteData.FIELD_CREATED_AT) ?: Timestamp.now()
            val updateAt = documentSnapshot.getTimestamp(RemoteData.FIELD_UPDATE_AT) ?: Timestamp.now()
            val userId = documentSnapshot.getString(RemoteData.FIELD_USER_ID) ?: ""
            val totalPrice = documentSnapshot.getDouble(RemoteData.FIELD_TOTAL_PRICE) ?: 0.0

            return CartModel(
                id = id,
                createdAt = createdAt,
                updateAt = updateAt,
                userId = userId,
                totalPrice = totalPrice
            )
        }
    }

}