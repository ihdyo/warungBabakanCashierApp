package com.babakan.cashier.data.dummy

import com.babakan.cashier.presentation.authentication.model.UserModel
import com.google.firebase.Timestamp

val dummyUserList = listOf(
    UserModel(
        id = "u1",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        name = "Kadek Michella",
        email = "kadekmichella@gmail.com",
        isActive = true,
        username = "kadek_m",
        isOwner = true
    ),
    UserModel(
        id = "u2",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        name = "Wahyu",
        email = "wahyu_tuhan@gmail.com",
        isActive = true,
        username = "wahyuwr",
        isOwner = false
    ),
    UserModel(
        id = "u3",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        name = "Ayu Lestari",
        email = "ayulestari@mail.com",
        isActive = false,
        username = "ayu_l",
        isOwner = false
    ),
    UserModel(
        id = "u4",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        name = "Budi Santoso",
        email = "budisantoso@domain.com",
        isActive = true,
        username = "budi_s",
        isOwner = false
    ),
    UserModel(
        id = "u5",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        name = "Sinta Rahayu",
        email = "sinta_rahayu@web.com",
        isActive = true,
        username = "sinta_r",
        isOwner = true
    )
)