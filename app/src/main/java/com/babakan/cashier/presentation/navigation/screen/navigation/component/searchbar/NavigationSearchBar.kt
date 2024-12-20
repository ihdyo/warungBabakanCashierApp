package com.babakan.cashier.presentation.navigation.screen.navigation.component.searchbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.babakan.cashier.R
import com.babakan.cashier.utils.constant.Constant
import com.babakan.cashier.utils.constant.MainScreenState
import com.babakan.cashier.utils.constant.SizeChart
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@Composable
fun MainSearchBar(
    scope: CoroutineScope,
    drawerState: DrawerState,
    isHome: Boolean,
    isReport: Boolean,
    isAdmin: Boolean,
    isAdminProduct: Boolean,
    isAdminCategory: Boolean,
    isAdminCashier: Boolean,
    isSearchActive: Boolean,
    snackBarHostState: SnackbarHostState,
    onSearchActiveChange: (Boolean) -> Unit,
    navController: NavController
) {
    val context = LocalContext.current

    SearchBar(
        query = "",
        onQueryChange = {},
        onSearch = {},
        active = isSearchActive,
        onActiveChange = { onSearchActiveChange(it) },
        placeholder = {
            Text(
                when {
                    isHome -> stringResource(R.string.productSearch)
                    isReport -> stringResource(R.string.reportSearch)
                    isAdmin -> when {
                        isAdminProduct -> stringResource(R.string.productSearch)
                        isAdminCategory -> stringResource(R.string.categorySearch)
                        isAdminCashier -> stringResource(R.string.cashierSearch)
                        else -> stringResource(R.string.search)
                    }
                    else -> stringResource(R.string.search)
                },
                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
            )
        },
        leadingIcon = {
            if (isSearchActive) {
                IconButton({ onSearchActiveChange(false) }) {
                    Icon(
                        Icons.AutoMirrored.Default.ArrowBack,
                        stringResource(R.string.back)
                    )
                }
            } else {
                IconButton({ scope.launch { drawerState.open() } }) {
                    Icon(
                        Icons.Default.Menu,
                        stringResource(R.string.menu)
                    )
                }
            }
        },
        trailingIcon = {
            if (isSearchActive) {
                IconButton({ onSearchActiveChange(false) }) {
                    Icon(
                        Icons.Default.Search,
                        stringResource(R.string.search)
                    )
                }
            } else {
                AnimatedVisibility(
                    visible = isHome,
                    enter = fadeIn(animationSpec = tween(Constant.ANIMATION_SHORT, easing = LinearOutSlowInEasing)),
                    exit = fadeOut(animationSpec = tween(Constant.ANIMATION_SHORT, easing = LinearOutSlowInEasing))
                ) {
                    IconButton({
                        scope.launch {
                            navController.navigate(MainScreenState.CART.name)
                        }
                    }) {
                        Icon(
                            Icons.Default.ShoppingCart,
                            stringResource(R.string.cart)
                        )
                    }
                }
                AnimatedVisibility(
                    visible = isAdmin,
                    enter = fadeIn(animationSpec = tween(Constant.ANIMATION_SHORT, easing = LinearOutSlowInEasing)),
                    exit = fadeOut(animationSpec = tween(Constant.ANIMATION_SHORT, easing = LinearOutSlowInEasing))
                ) {
                    IconButton(
                        {
                            scope.launch {
                                snackBarHostState.showSnackbar(context.getString(R.string.loginAsOwner))
                            }
                        }
                    ) {
                        Icon(
                            Icons.Default.VerifiedUser,
                            stringResource(R.string.admin),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = animateDpAsState(
                    label = stringResource(R.string.search),
                    targetValue = if (isSearchActive) SizeChart.SIZE_0.dp else SizeChart.DEFAULT_SPACE.dp
                ).value
            )
            .padding(bottom = SizeChart.BETWEEN_ITEMS.dp),
    ) {
     // TODO Search Page
    }
}