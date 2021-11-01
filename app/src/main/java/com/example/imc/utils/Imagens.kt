package com.example.imc.utils

import android.graphics.Bitmap
import android.util.Base64.encodeToString
import java.util.*

fun convertBitmapToBase64(bitmap: Bitmap) : String {
    val bitmapArray = ByteArrayOtputStream()

    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bitmapArray)

    Base64.encodeToString(bitmapArray.toByteArray(), Base64.DEFAULT)
}