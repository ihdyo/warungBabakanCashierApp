package com.babakan.cashier.common.item

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.babakan.cashier.R
import com.babakan.cashier.utils.constant.SizeChart
import com.babakan.cashier.utils.formatter.Formatter

@Composable
fun ProductItem(
    productImage: Int,
    productPrice: Int,
    productName: String,
    textValue: String,
    onValueChange: (String) -> Unit,
    onSubtract: () -> Unit,
    onAdd: () -> Unit
) {
    val onValueChangeToLong = textValue.toLongOrNull() ?: 0

    OutlinedCard(
        shape = MaterialTheme.shapes.large
    ) {
        Column {
            Box(
                Modifier.clip(MaterialTheme.shapes.large)
            ) {
                Image(
                    painter = painterResource(productImage),
                    contentDescription = stringResource(R.string.product),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(SizeChart.IMAGE_THUMBNAIL_HEIGHT.dp)
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
                        modifier = Modifier.padding(horizontal = SizeChart.SIZE_LG.dp, vertical = SizeChart.SIZE_XS.dp)
                    )
                }
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(SizeChart.BETWEEN_TEXTS.dp),
                modifier = Modifier.padding(SizeChart.SMALL_SPACE.dp)
            ) {
                Text(
                    productName,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                HorizontalDivider(
                    Modifier.padding(vertical = SizeChart.BETWEEN_TEXTS.dp)
                )
                Card(
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        IconButton(
                            enabled = onValueChangeToLong > 0,
                            onClick = onSubtract,
                            modifier = Modifier.size(SizeChart.SMALL_ICON_BUTTON.dp)
                        ) {
                            Icon(
                                Icons.Default.Remove,
                                contentDescription = stringResource(R.string.remove),
                            )
                        }
                        BasicTextField(
                            value = textValue,
                            onValueChange = { value ->
                                if (value.isEmpty()) {
                                    onValueChange("")
                                } else {
                                    val filteredValue = value.filter { it.isDigit() }
                                    if (filteredValue != value) {
                                        onValueChange(filteredValue)
                                    } else {
                                        onValueChange(value)
                                    }
                                }
                            },
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
                                        .fillMaxSize()
                                        .padding(vertical = SizeChart.SMALL_SPACE.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (textValue.isEmpty()) {
                                        Text(
                                            0.toString(),
                                            style = MaterialTheme.typography.titleMedium,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                    innerTextField()
                                }
                            },
                            modifier = Modifier
                                .height(SizeChart.SMALL_TEXT_FIELD.dp)
                                .weight(1f)
                                .background(MaterialTheme.colorScheme.secondaryContainer)
                                .padding(horizontal = SizeChart.SMALL_SPACE.dp)
                        )
                        IconButton(
                            onClick = onAdd,
                            Modifier.size(SizeChart.SMALL_ICON_BUTTON.dp)
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
