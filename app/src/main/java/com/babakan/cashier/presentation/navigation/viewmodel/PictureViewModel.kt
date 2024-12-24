package com.babakan.cashier.presentation.navigation.viewmodel

import android.graphics.Bitmap
import android.graphics.Picture
import androidx.lifecycle.ViewModel

class PictureViewModel : ViewModel() {
    var picture: Picture? = null
        private set

    var bitmap: Bitmap? = null
        private set

    fun setPicture(pic: Picture) {
        picture = pic
    }

    fun setBitmap(bmp: Bitmap) {
        bitmap = bmp
    }
}
