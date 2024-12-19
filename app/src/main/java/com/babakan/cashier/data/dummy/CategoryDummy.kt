package com.babakan.cashier.data.dummy

import com.babakan.cashier.presentation.owner.model.CategoryModel
import com.google.firebase.Timestamp

val dummyCategoryList = listOf(
    CategoryModel(
        id = "c1",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        name = "Makanan",
        iconUrl = "https://drive.google.com/uc?export=download&id=1pMBVtCsVDBWQRVjJdLSOf99KoYd5gYfh",
    ),
    CategoryModel(
        id = "c2",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        name = "Minuman",
        iconUrl = "https://drive.google.com/uc?export=download&id=1WImV0BwL855ydyJcom6nwAMXNxJQLnFE",
    ),
    CategoryModel(
        id = "c3",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        name = "Snack",
        iconUrl = "https://drive.google.com/uc?export=download&id=1s4R8btNp5UoOtbTAdWk3VlsFJ9DLE4my",
    ),
)