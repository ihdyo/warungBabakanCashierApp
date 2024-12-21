package com.babakan.cashier.common.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.babakan.cashier.common.component.CategoryComponent
import com.babakan.cashier.common.item.ProductOutItem
import com.babakan.cashier.data.dummy.dummyCategoryList
import com.babakan.cashier.data.dummy.dummyProductList
import com.babakan.cashier.presentation.owner.model.ProductOutModel
import com.babakan.cashier.presentation.owner.model.CategoryModel
import com.babakan.cashier.presentation.owner.model.ProductModel
import com.babakan.cashier.utils.constant.SizeChart

@Composable
fun ProductOutList(
    isEditable: Boolean,
    productOutItem: List<ProductOutModel>
) {

    val products = dummyProductList
    val categories = dummyCategoryList

    Column(
        verticalArrangement = Arrangement.spacedBy(SizeChart.SMALL_SPACE.dp),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        val groupedItems = productOutItem.groupBy { item ->
            val productItem = products.find { it.id == item.id }
            productItem?.categoryId
        }

        for ((categoryId, groupedProducts) in groupedItems) {
            val categoryItem = categories.find { it.id == categoryId } ?: CategoryModel()

            Spacer(Modifier.weight(1f))
            CategoryComponent(
                iconUrl = categoryItem.iconUrl,
                name = categoryItem.name
            )

            for (productOut in groupedProducts) {
                val productItem = products.find { it.id == productOut.id } ?: ProductModel()

                ProductOutItem(
                    index = groupedProducts.indexOf(productOut),
                    productItem = productItem,
                    productOutItem = productOut,
                    isEditable = isEditable,
                    textValue = productOut.quantity.toString(),
                    onTextChanged = { productOut.quantity = it.toInt() },
                    onSubtract = { productOut.quantity -= 1 },
                    onAdd = { productOut.quantity += 1 },
                )
            }
        }
    }
}