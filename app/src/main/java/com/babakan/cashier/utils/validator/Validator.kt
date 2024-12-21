package com.babakan.cashier.utils.validator

import android.content.Context
import com.babakan.cashier.R

object Validator {
    fun isNotEmpty(
        context: Context,
        value: String,
        fieldName: String
    ): String? {
        return if (value.isBlank()) context.getString(R.string.cannotEmpty, fieldName) else null
    }

    fun isValidEmail(
        context: Context,
        email: String
    ): String? {
        val emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$".toRegex()
        return if (!email.matches(emailRegex)) context.getString(R.string.invalidEmail) else null
    }

    fun isValidPassword(
        context: Context,
        password: String
    ): String? {
        return if (password.length < 6) context.getString(R.string.showPassword) else null
    }

    fun isPasswordMatch(
        context: Context,
        password: String,
        confirmPassword: String
    ): String? {
        return if (password != confirmPassword) context.getString(R.string.passwordNotMatch) else null
    }

    fun isValidUsername(
        context: Context,
        username: String
    ): String? {
        val usernameRegex = "^[a-zA-Z0-9_]+$".toRegex()
        return if (!username.matches(usernameRegex)) context.getString(R.string.invalidUsername) else null
    }

    fun isValidPrice(
        context: Context,
        price: String
    ): String? {
        val priceRegex = "^[0-9]+([.,][0-9]+)?$".toRegex()
        return if (!price.matches(priceRegex) || price.replace("[.,]".toRegex(), "").toDouble() <= 0) context.getString(R.string.invalidPrice) else null
    }

    fun isValidUrl(
        context: Context,
        url: String
    ): String? {
        val urlRegex = "^(http|https)://[a-zA-Z0-9\\-\\.]+(?:\\.[a-zA-Z]{2,})?(?:/[^\\s]*)?$".toRegex()
        return if (!url.matches(urlRegex)) context.getString(R.string.invalidUrl) else null
    }

}
