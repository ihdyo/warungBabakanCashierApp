package com.babakan.cashier.presentation.cashier.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.babakan.cashier.R
import com.babakan.cashier.common.item.ProductItem
import com.babakan.cashier.utils.constant.SizeChart

@ExperimentalMaterial3Api
@Composable
fun Home(
    nestedScrollConnection: NestedScrollConnection
) {
    LazyVerticalGrid(
        GridCells.Fixed(2),
        contentPadding = PaddingValues(SizeChart.DEFAULT_SPACE.dp),
        verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp),
        horizontalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_ITEMS.dp),
        modifier = Modifier.nestedScroll(nestedScrollConnection)
    ) {
        items(10) {
            var textValue by remember { mutableStateOf(TextFieldValue("")) }
            
            ProductItem(
                textValue = textValue.text,
                onValueChange = { textValue = TextFieldValue(it) },
                productImage = R.drawable.img_placeholder,
                productPrice = 10000,
                productName = "Product $it",
                onSubtract = { },
                onAdd = { }
            )
        }
    }
}