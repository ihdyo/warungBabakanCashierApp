package com.babakan.cashier.presentation.navigation.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.babakan.cashier.utils.constant.MainScreenState

data class NavigationBarModel(
    val name: String,
    val route: MainScreenState,
    val activeIcon: ImageVector,
    val inactiveIcon: ImageVector
)