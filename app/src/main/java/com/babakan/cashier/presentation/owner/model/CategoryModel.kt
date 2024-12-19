package com.babakan.cashier.presentation.owner.model

import com.babakan.cashier.utils.constant.RemoteData
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot

data class CategoryModel(
    val id: String = "",
    val createdAt: Timestamp = Timestamp.now(),
    var updateAt: Timestamp = Timestamp.now(),
    var name: String = "",
    var iconUrl: String = "",
) {
    fun toJson(): Map<String, Any> {
        return mapOf(
            RemoteData.FIELD_ID to id,
            RemoteData.FIELD_CREATED_AT to createdAt,
            RemoteData.FIELD_UPDATE_AT to updateAt,
            RemoteData.FIELD_NAME to name,
            RemoteData.FIELD_ICON_URL to iconUrl,
        )
    }

    companion object {
        fun fromDocumentSnapshot(documentSnapshot: DocumentSnapshot): CategoryModel {
            val id = documentSnapshot.getString(RemoteData.FIELD_ID) ?: ""
            val createdAt = documentSnapshot.getTimestamp(RemoteData.FIELD_CREATED_AT) ?: Timestamp.now()
            val updateAt = documentSnapshot.getTimestamp(RemoteData.FIELD_UPDATE_AT) ?: Timestamp.now()
            val name = documentSnapshot.getString(RemoteData.FIELD_NAME) ?: ""
            val iconUrl = documentSnapshot.getString(RemoteData.FIELD_ICON_URL) ?: ""

            return CategoryModel(
                id = id,
                createdAt = createdAt,
                updateAt = updateAt,
                name = name,
                iconUrl = iconUrl
            )
        }
    }

}