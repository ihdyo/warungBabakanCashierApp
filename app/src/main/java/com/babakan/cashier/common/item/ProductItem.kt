package com.babakan.cashier.common.item

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.babakan.cashier.common.builder.IconLoader
import com.babakan.cashier.common.builder.ImageLoader
import com.babakan.cashier.common.component.ItemCounterComponent
import com.babakan.cashier.presentation.owner.model.CategoryModel
import com.babakan.cashier.presentation.owner.model.ProductModel
import com.babakan.cashier.presentation.owner.viewmodel.TemporaryCartViewModel
import com.babakan.cashier.utils.constant.SizeChart
import com.babakan.cashier.utils.formatter.Formatter

@Composable
fun ProductItem(
    temporaryCartViewModel: TemporaryCartViewModel,
    index : Int,
    productItem: ProductModel,
    categoryItem: CategoryModel
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

    val productName = productItem.name
    val productImageUrl = productItem.imageUrl
    val productPrice = productItem.price
    val categoryIconUrl = categoryItem.iconUrl

    OutlinedCard(
        shape = MaterialTheme.shapes.large
    ) {
        Column {
            Box(
                Modifier.clip(MaterialTheme.shapes.large)
            ) {
                ImageLoader(
                    imageUrl = productImageUrl,
                    height = SizeChart.IMAGE_THUMBNAIL_HEIGHT.dp
                )
                Card(
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainer),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(SizeChart.SIZE_XS.dp)
                ) {
                    Text(
                        Formatter.currency(productPrice),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(
                            horizontal = SizeChart.SIZE_LG.dp,
                            vertical = SizeChart.SIZE_XS.dp
                        )
                    )
                }
                Card(
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer),
                    shape = MaterialTheme.shapes.large,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(SizeChart.SIZE_XS.dp)
                ) {
                    Box(Modifier.padding(SizeChart.SIZE_XS.dp)) {
                        IconLoader(
                            categoryIconUrl,
                            SizeChart.ICON_MEDIUM.dp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_TEXTS.dp),
                modifier = Modifier.padding(SizeChart.SMALL_SPACE.dp)
            ) {
                Text(
                    productName,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                HorizontalDivider(Modifier.padding(vertical = SizeChart.BETWEEN_TEXTS.dp))
                ItemCounterComponent(
                    textValue = textValue,
                    onTextChanged = onTextChanged,
                    onSubtract = onSubtract,
                    onAdd = onAdd
                )
            }
        }
    }
}