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
import com.babakan.cashier.common.composable.FullScreenImage
import com.babakan.cashier.common.composable.ProductItem
import com.babakan.cashier.utils.constant.Size

@ExperimentalMaterial3Api
@Composable
fun Home(
    nestedScrollConnection: NestedScrollConnection
) {
    var openFullscreenImage by remember { mutableStateOf(false) }
    var imageUrl by remember { mutableStateOf("") }
    var textValue by remember { mutableStateOf(TextFieldValue("")) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(Size.DEFAULT_SPACE.dp),
        verticalArrangement = Arrangement.spacedBy(Size.BETWEEN_ITEMS.dp),
        horizontalArrangement = Arrangement.spacedBy(Size.BETWEEN_ITEMS.dp),
        modifier = Modifier.nestedScroll(nestedScrollConnection)
    ) {
        items(4) {
            ProductItem(
                textValue = textValue.text,
                onValueChange = { textValue = TextFieldValue(it) },
                productImage = R.drawable.placeholder,
                productPrice = 10000,
                productName = "Product $it",
                onImageClick = {
                    openFullscreenImage = true
                    imageUrl = "https://images.unsplash.com/photo-1506744038136-46273834b3fb?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80"
                },
                onSubtract = { },
                onAdd = { }
            )
        }
    }
    if (openFullscreenImage) {
        FullScreenImage(
            imageUrl = imageUrl,
            onDismissRequest = { openFullscreenImage = false }
        )
    }
}