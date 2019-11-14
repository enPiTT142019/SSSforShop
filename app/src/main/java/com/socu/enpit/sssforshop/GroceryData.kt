package com.socu.enpit.sssforshop

import android.graphics.Bitmap

class GroceryData constructor(_title: String, _contents: String, _imageName: String, _bitmap: Bitmap, _date: String = "") {
    val title: String = _title
    val contents: String = _contents
    val imageName: String = _imageName
    val bitmap: Bitmap = _bitmap
    val date: String = _date
}