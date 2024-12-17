package com.babakan.cashier.presentation.navigation.screen.fab

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.babakan.cashier.R

@Composable
fun MainFab(
    isHome: Boolean,
    isAdmin: Boolean,
    isAdminProduct: Boolean,
    isAdminCategory: Boolean,
    isAdminCashier: Boolean,
    isCartEmpty: Boolean,
    isSearchActive: Boolean
) {
    AnimatedVisibility(
        visible = !isSearchActive && ((isHome && !isCartEmpty) || isAdmin),
        enter = slideInHorizontally(initialOffsetX = { fullWidth -> fullWidth }),
        exit = slideOutHorizontally(targetOffsetX = { fullWidth -> fullWidth })
    ) {
        FloatingActionButton(
            {
                when {
                    isHome -> {
                        // TODO Add To Cart
                    }
                    isAdminCashier -> {
                        // TODO Add Cashier
                    }
                    isAdminCategory -> {
                        // TODO Add Category
                    }
                    isAdminProduct -> {
                        // TODO Add Product
                    }
                }
            },
        ) {
            Icon(
                Icons.Default.Add,
                stringResource(R.string.add)
            )
        }
    }
}