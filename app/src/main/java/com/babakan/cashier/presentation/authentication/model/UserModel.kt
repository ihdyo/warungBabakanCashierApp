package com.babakan.cashier.presentation.authentication.model

import com.babakan.cashier.utils.constant.RemoteData
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot

data class UserModel(
    val id: String = "",
    val createdAt: Timestamp = Timestamp.now(),
    var updateAt: Timestamp = Timestamp.now(),
    var name: String = "",
    var username: String = "",
    var email: String = "",
    var isOwner: Boolean = false,
    var isActive: Boolean = false
) {
    
    fun toJson(): Map<String, Any> {
        return mapOf(
            RemoteData.FIELD_ID to id,
            RemoteData.FIELD_CREATED_AT to createdAt,
            RemoteData.FIELD_UPDATE_AT to updateAt,
            RemoteData.FIELD_NAME to name,
            RemoteData.FIELD_USERNAME to username,
            RemoteData.FIELD_EMAIL to email,
            RemoteData.FIELD_IS_OWNER to isOwner,
            RemoteData.FIELD_IS_ACTIVE to isActive
        )
    }
    
    companion object {
        fun fromDocumentSnapshot(documentSnapshot: DocumentSnapshot): UserModel {
            val id = documentSnapshot.getString(RemoteData.FIELD_ID) ?: ""
            val createdAt = documentSnapshot.getTimestamp(RemoteData.FIELD_CREATED_AT) ?: Timestamp.now()
            val updateAt = documentSnapshot.getTimestamp(RemoteData.FIELD_UPDATE_AT) ?: Timestamp.now()
            val name = documentSnapshot.getString(RemoteData.FIELD_NAME) ?: ""
            val username = documentSnapshot.getString(RemoteData.FIELD_USERNAME) ?: ""
            val email = documentSnapshot.getString(RemoteData.FIELD_EMAIL) ?: ""
            val isOwner = documentSnapshot.getBoolean(RemoteData.FIELD_IS_OWNER) ?: false
            val isActive = documentSnapshot.getBoolean(RemoteData.FIELD_IS_ACTIVE) ?: false
            
            return UserModel(
                id = id,
                createdAt = createdAt,
                updateAt = updateAt,
                name = name,
                username = username,
                email = email,
                isOwner = isOwner,
                isActive = isActive
            )
        }
    }
    
}