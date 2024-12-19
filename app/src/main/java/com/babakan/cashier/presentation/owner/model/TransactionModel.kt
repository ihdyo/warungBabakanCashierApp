package com.babakan.cashier.presentation.owner.model

import com.babakan.cashier.utils.constant.RemoteData
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot

data class TransactionModel(
    val transactionId: String = "",
    val createdAt: Timestamp = Timestamp.now(),
    var updateAt: Timestamp = Timestamp.now(),
    var userId: String = "",
    var tableNumber: Int = 0,
    var customerName: String = "",
    var totalPrice: Double = 0.0,
    var notes: String = "",
) {

    fun toJson(): Map<String, Any> {
        return mapOf(
            RemoteData.FIELD_TRANSACTION_ID to transactionId,
            RemoteData.FIELD_CREATED_AT to createdAt,
            RemoteData.FIELD_UPDATE_AT to updateAt,
            RemoteData.FIELD_USER_ID to userId,
            RemoteData.FIELD_TABLE_NUMBER to tableNumber,
            RemoteData.FIELD_CUSTOMER_NAME to customerName,
            RemoteData.FIELD_TOTAL_PRICE to totalPrice,
            RemoteData.FIELD_NOTES to notes
        )
    }

    companion object {
        fun fromDocumentSnapshot(documentSnapshot: DocumentSnapshot): TransactionModel {
            val transactionId = documentSnapshot.getString(RemoteData.FIELD_TRANSACTION_ID) ?: ""
            val createdAt = documentSnapshot.getTimestamp(RemoteData.FIELD_CREATED_AT) ?: Timestamp.now()
            val updateAt = documentSnapshot.getTimestamp(RemoteData.FIELD_UPDATE_AT) ?: Timestamp.now()
            val userId = documentSnapshot.getString(RemoteData.FIELD_USER_ID) ?: ""
            val tableNumber = documentSnapshot.getLong(RemoteData.FIELD_TABLE_NUMBER) ?: 0
            val customerName = documentSnapshot.getString(RemoteData.FIELD_CUSTOMER_NAME) ?: ""
            val totalPrice = documentSnapshot.getDouble(RemoteData.FIELD_TOTAL_PRICE) ?: 0.0
            val notes = documentSnapshot.getString(RemoteData.FIELD_NOTES) ?: ""

            return TransactionModel(
                transactionId = transactionId,
                createdAt = createdAt,
                updateAt = updateAt,
                userId = userId,
                tableNumber = tableNumber.toInt(),
                customerName = customerName,
                totalPrice = totalPrice,
                notes = notes
            )
        }
    }

}