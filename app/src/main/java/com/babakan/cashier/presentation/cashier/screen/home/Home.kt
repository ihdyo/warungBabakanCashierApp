package com.babakan.cashier.presentation.cashier.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.babakan.cashier.common.item.ProductThumbnailItem
import com.babakan.cashier.common.style.pageContentPadding
import com.babakan.cashier.data.dummy.dummyCategoryList
import com.babakan.cashier.data.dummy.dummyProductList
import com.babakan.cashier.presentation.owner.model.CategoryModel
import com.babakan.cashier.presentation.owner.viewmodel.TemporaryCartViewModel
import com.babakan.cashier.utils.constant.SizeChart

@ExperimentalMaterial3Api
@Composable
fun Home(
    temporaryCartViewModel: TemporaryCartViewModel,
    nestedScrollConnection: NestedScrollConnection,
    isFabShown: Boolean
) {

    val product = dummyProductList
    val category = dummyCategoryList

    Box(Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            GridCells.Fixed(2),
            contentPadding = pageContentPadding(isFabShown),
            verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp),
            horizontalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp),
            modifier = Modifier
                .nestedScroll(nestedScrollConnection)
        ) {
            itemsIndexed(product) { index, item ->
                val categoryItem = category.find { it.id == item.categoryId } ?: CategoryModel()

                ProductThumbnailItem(
                    temporaryCartViewModel = temporaryCartViewModel,
                    index = index,
                    productItem = item,
                    categoryItem = categoryItem
                )
            }
        }
    }
}