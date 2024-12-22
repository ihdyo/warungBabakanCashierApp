package com.babakan.cashier.common.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.babakan.cashier.R
import com.babakan.cashier.utils.builder.ImageLoader
import com.babakan.cashier.common.component.ItemCounterComponent
import com.babakan.cashier.data.state.UiState
import com.babakan.cashier.presentation.owner.model.ProductOutModel
import com.babakan.cashier.presentation.owner.model.ProductModel
import com.babakan.cashier.presentation.owner.viewmodel.ProductViewModel
import com.babakan.cashier.utils.constant.SizeChart
import com.babakan.cashier.utils.formatter.Formatter

@Composable
fun ProductOutItem(
    productViewModel: ProductViewModel = viewModel(),
    index: Int,
    productItem: ProductModel,
    productOutItem: ProductOutModel,
    isEditable: Boolean = false,
    isCart: Boolean = false,
    textValue: String,
    onTextChanged: (String) -> Unit,
    onSubtract: () -> Unit,
    onAdd: () -> Unit
) {
    val productsState by productViewModel.fetchProductsState.collectAsState()

    var products by remember { mutableStateOf(emptyList<ProductModel>()) }

    var showLoading by remember { mutableStateOf(false) }

    LaunchedEffect(productsState) {
        if (productsState is UiState.Loading) {
            showLoading = true
        } else if (productsState is UiState.Success) {
            showLoading = false
            products = (productsState as UiState.Success<List<ProductModel>>).data
        }
    }

    val realPrice = products.find { it.id == productOutItem.productId }?.price ?: 0.0

    Card(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainer),
    ) {
        Row(
            verticalAlignment = if (isEditable) Alignment.CenterVertically else Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(SizeChart.SMALL_SPACE.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(SizeChart.SMALL_SPACE.dp),
                modifier = Modifier.weight(if (isEditable) 2f else 1f)
            ) {
                Card(
                    shape = MaterialTheme.shapes.small
                ) {
                    ImageLoader(
                        productItem.imageUrl,
                        SizeChart.IMAGE_LIST_HEIGHT.dp,
                        SizeChart.IMAGE_LIST_HEIGHT.dp
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_TEXTS.dp)
                ) {
                    Text(
                        productItem.name,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        Formatter.currency(if (isCart) realPrice else productOutItem.price),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
            if (isEditable) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    ItemCounterComponent(
                        textValue = textValue,
                        onSubtract = onSubtract,
                        onAdd = onAdd,
                        onTextChanged = onTextChanged
                    )
                }
            } else {
                Text(
                    stringResource(R.string.countItem, productOutItem.quantity),
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
    }
}