package com.babakan.cashier.data.ui

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AdminPanelSettings
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Home
import com.babakan.cashier.R
import com.babakan.cashier.presentation.navigation.model.NavigationBarModel
import com.babakan.cashier.utils.constant.MainScreenState

fun listOfNavigationItems(context: Context): List<NavigationBarModel> {

    return listOf(
        NavigationBarModel(
            name = context.getString(R.string.home),
            route = MainScreenState.HOME,
            activeIcon = Icons.Filled.Home,
            inactiveIcon = Icons.Outlined.Home
        ),
        NavigationBarModel(
            name = context.getString(R.string.report),
            route = MainScreenState.REPORT,
            activeIcon = Icons.Filled.Description,
            inactiveIcon = Icons.Outlined.Description
        ),
        NavigationBarModel(
            name = context.getString(R.string.admin),
            route = MainScreenState.ADMIN,
            activeIcon = Icons.Filled.AdminPanelSettings,
            inactiveIcon = Icons.Outlined.AdminPanelSettings
        ),
    )
}