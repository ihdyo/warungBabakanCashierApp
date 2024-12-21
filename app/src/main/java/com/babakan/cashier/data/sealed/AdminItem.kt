package com.babakan.cashier.data.sealed

import com.babakan.cashier.presentation.authentication.model.UserModel
import com.babakan.cashier.presentation.owner.model.CategoryModel
import com.babakan.cashier.presentation.owner.model.ProductModel

sealed class AdminItem {
    data class Product(val product: ProductModel) : AdminItem()
    data class Category(val category: CategoryModel) : AdminItem()
    data class User(val user: UserModel) : AdminItem()
}