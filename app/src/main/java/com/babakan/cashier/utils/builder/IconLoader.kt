package com.babakan.cashier.utils.builder

import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.svg.SvgDecoder
import com.babakan.cashier.R

@Composable
fun IconLoader(
    iconUrl: String,
    size: Dp,
    color: Color = MaterialTheme.colorScheme.onSurface
) {
    val context = LocalContext.current
    val imageLoader = remember {
        ImageLoader.Builder(context)
            .components { add(SvgDecoder.Factory()) }
            .build()
    }

    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(iconUrl)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .crossfade(true)
            .build(),
        imageLoader = imageLoader,
        error = painterResource(R.drawable.ic_placeholder),
        contentDescription = stringResource(R.string.menu),
        contentScale = ContentScale.Crop,
        colorFilter = ColorFilter.tint(color),
        modifier = Modifier.size(size)
    )
}
