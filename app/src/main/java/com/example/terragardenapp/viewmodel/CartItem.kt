package com.example.terragardenapp.viewmodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartItem(
    val id: String,
    val name: String,
    val price: Double,
    val quantity: Int = 1
) : Parcelable
