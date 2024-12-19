package com.babakan.cashier.presentation.navigation.screen.navigation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.babakan.cashier.R
import com.babakan.cashier.data.ui.listOfNavigationItems
import com.babakan.cashier.utils.constant.Constant
import com.babakan.cashier.utils.constant.MainScreenState
import com.babakan.cashier.utils.constant.SizeChart

@Composable
fun NavigationBottomBar(
    navController: NavController,
    currentDestination: String?,
    isSearchActive: Boolean
) {
    val isAdmin = true

    AnimatedVisibility(
        visible = isAdmin && !isSearchActive && currentDestination != MainScreenState.CART.name,
        enter = slideInVertically(initialOffsetY = { it }, animationSpec = tween(Constant.ANIMATION_SHORT, easing = LinearOutSlowInEasing)),
        exit = slideOutVertically(targetOffsetY = { it }, animationSpec = tween(Constant.ANIMATION_SHORT, easing = LinearOutSlowInEasing))
    ) {
        NavigationBar(
            Modifier
                .fillMaxWidth()
                .height(SizeChart.NAV_BAR_HEIGHT.dp)
        ) {
            listOfNavigationItems(LocalContext.current).forEach { item ->
                val isCurrentPage = currentDestination == item.route.name

                NavigationBarItem(
                    isCurrentPage,
                    { if (!isCurrentPage) {
                        navController.navigate(item.route.name) {
                            popUpTo(0)
                            launchSingleTop = true
                        }
                    } },
                    { Icon(
                        if (isCurrentPage) item.activeIcon else item.inactiveIcon,
                        item.route.name,
                        tint = MaterialTheme.colorScheme.primary
                    ) },
                    label = {
                        Text(
                            item.name,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    },
                    alwaysShowLabel = isCurrentPage
                )
            }
        }
    }
}