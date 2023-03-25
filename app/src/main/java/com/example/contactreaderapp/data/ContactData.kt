package com.example.contactreaderapp.data

import android.graphics.Bitmap

data class ContactData(
    val name: String,
    val surname:String?,
    val phone:String,
    val image: Bitmap?
)
