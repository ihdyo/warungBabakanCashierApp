package com.babakan.cashier.presentation.owner.model

import java.sql.Timestamp

data class ProductModel(
    val documentId: String,
    val createdAt: Timestamp,
    var updateAt: Timestamp,
    var name: String,
    var category: String,
    var imageUrl: String,
    var price: Int,
)

val dummyProductList = listOf(
    ProductModel(
        documentId = "1",
        createdAt = Timestamp(System.currentTimeMillis()),
        updateAt = Timestamp(System.currentTimeMillis()),
        name = "Indomie",
        category = "Food",
        imageUrl = "https://img.freepik.com/premium-vector/window-operating-system-error-warning-dialog-window-popup-message-with-system-failure-flat-design_812892-54.jpg",
        price = 3500
    ),
    ProductModel(
        documentId = "2",
        createdAt = Timestamp(System.currentTimeMillis()),
        updateAt = Timestamp(System.currentTimeMillis()),
        name = "Kopi",
        category = "Drink",
        imageUrl = "https://images.unsplash.com/photo-1675435644687-562e8042b9db",
        price = 2500
    ),
    ProductModel(
        documentId = "3",
        createdAt = Timestamp(System.currentTimeMillis()),
        updateAt = Timestamp(System.currentTimeMillis()),
        name = "Teh",
        category = "Drink",
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/never-ending-cf10b.appspot.com/o/image%2F3ToEINm9nkvs2YnVnWYg%2Fimg_0.jpg?alt=media&token=1140909e-d4cf-4952-a2de-d207782f00b8",
        price = 1500
    ),
    ProductModel(
        documentId = "4",
        createdAt = Timestamp(System.currentTimeMillis()),
        updateAt = Timestamp(System.currentTimeMillis()),
        name = "Roti",
        category = "Food",
        imageUrl = "https://images.unsplash.com/photo-1509440159596-0249088772ff?q=80&w=2072&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        price = 2000
    ),
    ProductModel(
        documentId = "51",
        createdAt = Timestamp(System.currentTimeMillis()),
        updateAt = Timestamp(System.currentTimeMillis()),
        name = "Kopi",
        category = "Drink",
        imageUrl = "https://images.unsplash.com/photo-1675435644687-562e8042b9db",
        price = 2500
    ),
    ProductModel(
        documentId = "61",
        createdAt = Timestamp(System.currentTimeMillis()),
        updateAt = Timestamp(System.currentTimeMillis()),
        name = "Teh",
        category = "Drink",
        imageUrl = "https://firebasestorage.googleapis.com/v0/b/never-ending-cf10b.appspot.com/o/image%2F3ToEINm9nkvs2YnVnWYg%2Fimg_0.jpg?alt=media&token=1140909e-d4cf-4952-a2de-d207782f00b8",
        price = 1500
    ),
)