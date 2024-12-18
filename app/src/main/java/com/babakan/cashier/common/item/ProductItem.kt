package com.babakan.cashier.common.item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.babakan.cashier.R
import com.babakan.cashier.presentation.owner.model.ProductModel
import com.babakan.cashier.presentation.owner.viewmodel.TemporaryCartViewModel
import com.babakan.cashier.utils.constant.SizeChart
import com.babakan.cashier.utils.formatter.Formatter

@Composable
fun ProductItem(
    temporaryCartViewModel: TemporaryCartViewModel,
    index : Int,
    item: ProductModel
) {
    val temporaryProduct by temporaryCartViewModel.temporaryCartProduct.collectAsState()

    var textValue by remember {
        val existingQuantity = temporaryProduct.find { it.containsKey(item.documentId) }?.get(item.documentId) ?: 0
        mutableStateOf(existingQuantity.toString())
    }

    LaunchedEffect(temporaryProduct) {
        val currentQuantity = temporaryProduct.find { it.containsKey(item.documentId) }?.get(item.documentId) ?: 0
        textValue = currentQuantity.toString()
    }

    val onSubtract: () -> Unit = {
        val currentValue = (textValue.toIntOrNull() ?: 0).coerceAtLeast(1)
        val newValue = (currentValue - 1).coerceAtLeast(0)
        textValue = newValue.toString()
        temporaryCartViewModel.addProductToTemporaryCart(item.documentId, -1)
    }

    val onAdd: () -> Unit = {
        val currentValue = (textValue.toIntOrNull() ?: 0)
        val newValue = currentValue + 1
        textValue = newValue.toString()
        temporaryCartViewModel.addProductToTemporaryCart(item.documentId, 1)
    }

    val onTextChanged: (String) -> Unit = { value ->
        val filteredValue = value.filter { it.isDigit() }
        val newValue = filteredValue.toIntOrNull() ?: 0
        textValue = newValue.toString()

        val diff = newValue - (temporaryProduct.find { it.containsKey(item.documentId) }?.get(item.documentId) ?: 0)
        temporaryCartViewModel.addProductToTemporaryCart(item.documentId, diff)
    }

    val productName = item.name
    val productImage = item.imageUrl
    val productPrice = item.price

    OutlinedCard(
        shape = MaterialTheme.shapes.large
    ) {
        Column {
            Box(
                Modifier.clip(MaterialTheme.shapes.large)
            ) {
                val imageLoader = ImageLoader.Builder(LocalContext.current)
                    .logger(DebugLogger())
                    .build()

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .memoryCachePolicy(CachePolicy.ENABLED)
                        .data(productImage)
                        .size(SizeChart.IMAGE_THUMBNAIL_HEIGHT)
                        .crossfade(true)
                        .build(),
                    error = painterResource(R.drawable.img_placeholder),
                    contentDescription = stringResource(R.string.product),
                    contentScale = ContentScale.Crop,
                    imageLoader = imageLoader,
                    modifier = Modifier.height(SizeChart.IMAGE_THUMBNAIL_HEIGHT.dp)
                )
                Card(
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainer),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(SizeChart.SMALL_SPACE.dp)
                ) {
                    Text(
                        Formatter.currency(productPrice.toLong()),
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(
                            horizontal = SizeChart.SIZE_LG.dp,
                            vertical = SizeChart.SIZE_XS.dp
                        )
                    )
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

                Card(
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(
                            enabled = (textValue.toIntOrNull() ?: 0) > 0,
                            onClick = onSubtract,
                            modifier = Modifier.size(SizeChart.SMALL_ICON_BUTTON.dp).weight(1f)
                        ) {
                            Icon(
                                Icons.Default.Remove,
                                contentDescription = stringResource(R.string.remove),
                            )
                        }
                        BasicTextField(
                            value = textValue,
                            onValueChange = onTextChanged,
                            singleLine = true,
                            textStyle = MaterialTheme.typography.titleMedium.copy(
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                textAlign = TextAlign.Center
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            cursorBrush = SolidColor(MaterialTheme.colorScheme.onSecondaryContainer),
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier
                                        .height(SizeChart.SMALL_TEXT_FIELD.dp)
                                        .weight(1f)
                                        .background(MaterialTheme.colorScheme.secondaryContainer)
                                        .padding(vertical = SizeChart.SMALL_SPACE.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    innerTextField()
                                }
                            },
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = onAdd,
                            modifier = Modifier.size(SizeChart.SMALL_ICON_BUTTON.dp).weight(1f)
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = stringResource(R.string.add),
                            )
                        }
                    }
                }
            }
        }
    }
}