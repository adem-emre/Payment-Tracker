package com.example.paymenttracker.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Payment(
    val id: Int?,
    val cost: Double,
    val paymentDate: String,
    val paymentTypeId: Int
) : Parcelable
