package com.babakan.cashier.common.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.babakan.cashier.utils.builder.ImageLoader
import com.babakan.cashier.common.component.CategoryComponent
import com.babakan.cashier.common.component.EditButton
import com.babakan.cashier.common.component.ItemCounterComponent
import com.babakan.cashier.presentation.owner.model.CategoryModel
import com.babakan.cashier.presentation.owner.model.ProductModel
import com.babakan.cashier.presentation.owner.viewmodel.TemporaryCartViewModel
import com.babakan.cashier.utils.constant.SizeChart
import com.babakan.cashier.utils.formatter.Formatter

@Composable
fun ProductItem(
    temporaryCartViewModel: TemporaryCartViewModel = viewModel(),
    index: Int,
    productItem: ProductModel,
    categoryItem: CategoryModel,
    isCountable: Boolean = false,
    isAdmin: Boolean = false,
    onAdminEdit: (ProductModel) -> Unit = {}
) {

    val temporaryProduct by temporaryCartViewModel.temporaryCartProduct.collectAsState()

    var textValue by remember {
        val existingQuantity = temporaryProduct.find { it.containsKey(productItem.id) }?.get(productItem.id) ?: 0
        mutableStateOf(existingQuantity.toString())
    }

    LaunchedEffect(temporaryProduct) {
        val currentQuantity = temporaryProduct.find { it.containsKey(productItem.id) }?.get(productItem.id) ?: 0
        textValue = currentQuantity.toString()
    }

    val onSubtract: () -> Unit = {
        val currentValue = (textValue.toIntOrNull() ?: 0).coerceAtLeast(1)
        val newValue = (currentValue - 1).coerceAtLeast(0)
        textValue = newValue.toString()
        temporaryCartViewModel.addProductToTemporaryCart(productItem.id, -1)
    }

    val onAdd: () -> Unit = {
        val currentValue = (textValue.toIntOrNull() ?: 0)
        val newValue = currentValue + 1
        textValue = newValue.toString()
        temporaryCartViewModel.addProductToTemporaryCart(productItem.id, 1)
    }

    val onTextChanged: (String) -> Unit = { value ->
        val filteredValue = value.filter { it.isDigit() }
        val newValue = filteredValue.toIntOrNull() ?: 0
        textValue = newValue.toString()

        val diff = newValue - (temporaryProduct.find { it.containsKey(productItem.id) }?.get(productItem.id) ?: 0)
        temporaryCartViewModel.addProductToTemporaryCart(productItem.id, diff)
    }

    Card(colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainer)) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(SizeChart.SMALL_SPACE.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(SizeChart.SMALL_SPACE.dp),
                modifier = Modifier.weight(if (isCountable) 2f else 1f).height(IntrinsicSize.Min)
            ) {
                Card(
                    shape = MaterialTheme.shapes.small
                ) {
                    ImageLoader(
                        productItem.imageUrl,
                        SizeChart.IMAGE_ADMIN_HEIGHT.dp,
                        SizeChart.IMAGE_ADMIN_HEIGHT.dp
                    )
                }
                Column(
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                ) {
                    CategoryComponent(
                        name = categoryItem.name,
                        iconUrl = categoryItem.iconUrl
                    )
                    Text(
                        productItem.name,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        Formatter.currency(productItem.price),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
            if (isCountable) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.Bottom)
                ) {
                    ItemCounterComponent(
                        textValue = textValue,
                        onTextChanged = { onTextChanged(it) },
                        onSubtract = { onSubtract() },
                        onAdd = { onAdd() }
                    )
                }
            }
            if (isAdmin && !isCountable) { EditButton { onAdminEdit(productItem) } }
        }
    }
}