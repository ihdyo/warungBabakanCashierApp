package com.babakan.cashier.presentation.owner.screen.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.babakan.cashier.data.sealed.AdminItem
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.authentication.model.UserModel
import com.babakan.cashier.presentation.owner.model.CategoryModel
import com.babakan.cashier.presentation.owner.model.ProductModel
import com.babakan.cashier.presentation.owner.screen.admin.user.AdminUser
import com.babakan.cashier.presentation.owner.screen.admin.category.AdminCategory
import com.babakan.cashier.presentation.owner.screen.admin.product.AdminProduct
import com.babakan.cashier.presentation.owner.viewmodel.CategoryViewModel
import com.babakan.cashier.presentation.owner.viewmodel.ProductViewModel
import com.babakan.cashier.presentation.owner.viewmodel.UserViewModel
import com.babakan.cashier.utils.constant.AuditState

@ExperimentalMaterial3Api
@Composable
fun Admin(
    productViewModel: ProductViewModel = viewModel(),
    categoryViewModel: CategoryViewModel = viewModel(),
    userViewModel: UserViewModel = viewModel(),
    nestedScrollConnection: NestedScrollConnection,
    pagerState: PagerState,
    onSelectedAdminTabIndex: (Int) -> Unit,
    onAuditStateChange: (AuditState) -> Unit,
    onItemSelected: (AdminItem) -> Unit,
) {
    val productsState by productViewModel.fetchProductsState.collectAsState()
    val categoriesState by categoryViewModel.fetchCategoriesState.collectAsState()
    val usersState by userViewModel.fetchUsersState.collectAsState()

    var products by remember { mutableStateOf(emptyList<ProductModel>()) }
    var categories by remember { mutableStateOf(emptyList<CategoryModel>()) }
    var users by remember { mutableStateOf(emptyList<UserModel>()) }

    var showLoading by remember { mutableStateOf(false) }

    LaunchedEffect(productsState) {
        if (productsState is UiState.Loading) {
            showLoading = true
        } else if (productsState is UiState.Success) {
            showLoading = false
            products = (productsState as UiState.Success<List<ProductModel>>).data
        }
    }
    LaunchedEffect(categoriesState) {
        if (categoriesState is UiState.Loading) {
            showLoading = true
        } else if (categoriesState is UiState.Success) {
            showLoading = false
            categories = (categoriesState as UiState.Success<List<CategoryModel>>).data
        }
    }
    LaunchedEffect(usersState) {
        if (usersState is UiState.Loading) {
            showLoading = true
        } else if (usersState is UiState.Success) {
            showLoading = false
            users = (usersState as UiState.Success<List<UserModel>>).data
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        onSelectedAdminTabIndex(pagerState.currentPage)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(nestedScrollConnection),
        verticalArrangement = Arrangement.Top
    ) {
        HorizontalPager(
            pagerState,
            Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> AdminProduct(
                    products = products,
                    categories = categories,
                    nestedScrollConnection = nestedScrollConnection,
                    onAuditStateChange = onAuditStateChange,
                    onItemSelected = { item ->
                        onItemSelected(AdminItem.Product(item))
                    },
                    showLoading = showLoading
                )
                1 -> AdminCategory(
                    categories = categories,
                    nestedScrollConnection = nestedScrollConnection,
                    onAuditStateChange = onAuditStateChange,
                    onItemSelected = { item ->
                        onItemSelected(AdminItem.Category(item))
                    },
                    showLoading = showLoading
                )
                2 -> AdminUser(
                    users = users,
                    nestedScrollConnection = nestedScrollConnection,
                    onAuditStateChange = onAuditStateChange,
                    onItemSelected = { item ->
                        onItemSelected(AdminItem.User(item))
                    },
                    showLoading = showLoading
                )
            }
        }
    }
}