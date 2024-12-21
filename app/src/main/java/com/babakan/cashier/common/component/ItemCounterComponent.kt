package com.babakan.cashier.common.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.babakan.cashier.R
import com.babakan.cashier.utils.constant.SizeChart

@Composable
fun ItemCounterComponent(
    textValue: String,
    onTextChanged: (String) -> Unit,
    onSubtract: () -> Unit,
    onAdd: () -> Unit
) {

//    {
//        selectedCategory.setTextAndPlaceCursorAtEnd(item.name)
//        showCategories = false
//    },

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