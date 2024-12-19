package com.babakan.cashier.data.dummy

import com.babakan.cashier.presentation.owner.model.TransactionModel
import com.google.firebase.Timestamp

val dummyTransactionList = listOf(
    TransactionModel(
        transactionId = "191220240001",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        userId = "u1",
        tableNumber = 1,
        customerName = "John Doe",
        totalPrice = 57000.0,
        notes = "Extra spicy"
    ),
    TransactionModel(
        transactionId = "191220240002",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        userId = "u2",
        tableNumber = 2,
        customerName = "Jane Smith",
        totalPrice = 43000.0,
        notes = "No peanuts"
    ),
    TransactionModel(
        transactionId = "191220240003",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        userId = "u3",
        tableNumber = 3,
        customerName = "Michael Tan",
        totalPrice = 30000.5,
        notes = "Less ice in the drinks"
    ),
    TransactionModel(
        transactionId = "191220240004",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        userId = "u4",
        tableNumber = 4,
        customerName = "Emily Clark",
        totalPrice = 12500.0,
        notes = "Gluten-free bread"
    ),
    TransactionModel(
        transactionId = "191220240005",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        userId = "u1",
        tableNumber = 5,
        customerName = "David Lee",
        totalPrice = 45000.0,
        notes = "Medium rare steak"
    ),
    TransactionModel(
        transactionId = "191220240006",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        userId = "u2",
        tableNumber = 6,
        customerName = "Samantha Wright",
        totalPrice = 8000.0,
        notes = "Vegan options only"
    ),
    TransactionModel(
        transactionId = "191220240007",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        userId = "u3",
        tableNumber = 7,
        customerName = "Brian O'Conner",
        totalPrice = 23000.5,
        notes = "Extra napkins"
    ),
    TransactionModel(
        transactionId = "191220240008",
        createdAt = Timestamp.now(),
        updateAt = Timestamp.now(),
        userId = "u4",
        tableNumber = 8,
        customerName = "Jessica Miller",
        totalPrice = 9000.0,
        notes = "Allergic to seafood"
    )
)
