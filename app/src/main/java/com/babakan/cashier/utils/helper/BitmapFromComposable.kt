package com.babakan.cashier.utils.helper

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Picture
import android.net.Uri
import android.content.Intent
import android.content.Intent.createChooser
import android.provider.MediaStore
import com.babakan.cashier.R

fun createBitmapFromPicture(picture: Picture): Bitmap {
    val bitmap = Bitmap.createBitmap(
        picture.width,
        picture.height,
        Bitmap.Config.ARGB_8888
    )

    val canvas = android.graphics.Canvas(bitmap)
    canvas.drawColor(android.graphics.Color.WHITE)
    canvas.drawPicture(picture)
    return bitmap
}

fun Bitmap.saveToDisk(
    context: Context,
    transactionId: String,
    onResult: (Boolean, Uri?) -> Unit
) {
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, "${transactionId}.png")
        put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/babakan")
    }

    val uri = context.contentResolver.insert(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues
    )

    if (uri == null) {
        onResult(false, null)
        return
    }

    try {
        context.contentResolver.openOutputStream(uri).use { outputStream ->
            if (outputStream != null) {
                compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.flush()
                onResult(true, uri)
            } else {
                onResult(false, null)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        onResult(false, null)
    }
}

fun shareBitmap(
    context: Context,
    uri: Uri
) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "image/png"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    context.startActivity(createChooser(intent, context.getString(R.string.shareInvoice)))
}