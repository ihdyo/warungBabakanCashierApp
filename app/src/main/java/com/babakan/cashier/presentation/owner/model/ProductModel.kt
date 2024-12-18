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
        imageUrl = "https://cdn.antaranews.com/cache/1200x800/2024/07/08/indomie-bangladesh.jpg",
        price = 3500
    ),
    ProductModel(
        documentId = "2",
        createdAt = Timestamp(System.currentTimeMillis()),
        updateAt = Timestamp(System.currentTimeMillis()),
        name = "Kopi",
        category = "Drink",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/45/A_small_cup_of_coffee.JPG/800px-A_small_cup_of_coffee.JPG",
        price = 2500
    ),
    ProductModel(
        documentId = "3",
        createdAt = Timestamp(System.currentTimeMillis()),
        updateAt = Timestamp(System.currentTimeMillis()),
        name = "Teh",
        category = "Drink",
        imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQIgsZv19mTG2mMCSneHlnFRrswXhHho2usvA&s",
        price = 1500
    ),
    ProductModel(
        documentId = "4",
        createdAt = Timestamp(System.currentTimeMillis()),
        updateAt = Timestamp(System.currentTimeMillis()),
        name = "Roti",
        category = "Food",
        imageUrl = "https://www.allrecipes.com/thmb/CjzJwg2pACUzGODdxJL1BJDRx9Y=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/6788-amish-white-bread-DDMFS-4x3-6faa1e552bdb4f6eabdd7791e59b3c84.jpg",
        price = 2000
    ),
    ProductModel(
        documentId = "5",
        createdAt = Timestamp(System.currentTimeMillis()),
        updateAt = Timestamp(System.currentTimeMillis()),
        name = "Nasi Goreng",
        category = "Food",
        imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSdQ2BQLtpdIm5FgecGcx-1ZjDd_g2bM5TtlQ&s",
        price = 2500
    ),
    ProductModel(
        documentId = "6",
        createdAt = Timestamp(System.currentTimeMillis()),
        updateAt = Timestamp(System.currentTimeMillis()),
        name = "Thai Tea",
        category = "Drink",
        imageUrl = "https://flash-coffee.com/id/wp-content/uploads/sites/13/2023/06/THAI-TEA.png",
        price = 1500
    ),
)