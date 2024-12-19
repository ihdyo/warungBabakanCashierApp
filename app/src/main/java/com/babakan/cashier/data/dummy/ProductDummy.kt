package com.babakan.cashier.data.dummy

import com.babakan.cashier.presentation.owner.model.ProductModel
import com.google.firebase.Timestamp

val dummyProductList = listOf(
    ProductModel(
        id = "p1",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        name = "Indomie",
        categoryId = "c1",
        imageUrl = "https://cdn.antaranews.com/cache/1200x800/2024/07/08/indomie-bangladesh.jpg",
        price = 7000.0
    ),
    ProductModel(
        id = "p2",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        name = "Kopi",
        categoryId = "c2",
        imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/4/45/A_small_cup_of_coffee.JPG/800px-A_small_cup_of_coffee.JPG",
        price = 12000.0
    ),
    ProductModel(
        id = "p3",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        name = "Teh",
        categoryId = "c2",
        imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQIgsZv19mTG2mMCSneHlnFRrswXhHho2usvA&s",
        price = 3000.0
    ),
    ProductModel(
        id = "p4",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        name = "Roti",
        categoryId = "c1",
        imageUrl = "https://www.allrecipes.com/thmb/CjzJwg2pACUzGODdxJL1BJDRx9Y=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/6788-amish-white-bread-DDMFS-4x3-6faa1e552bdb4f6eabdd7791e59b3c84.jpg",
        price = 7000.0
    ),
    ProductModel(
        id = "p5",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        name = "Nasi Goreng",
        categoryId = "c1",
        imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSdQ2BQLtpdIm5FgecGcx-1ZjDd_g2bM5TtlQ&s",
        price = 15000.0
    ),
    ProductModel(
        id = "p6",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        name = "Thai Tea",
        categoryId = "c2",
        imageUrl = "https://flash-coffee.com/id/wp-content/uploads/sites/13/2023/06/THAI-TEA.png",
        price = 15000.0
    ),
)