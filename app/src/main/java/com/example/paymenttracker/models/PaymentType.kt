package com.example.paymenttracker.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaymentType(
    val id: Int?,
    val title: String,
    val periodType: String?,
    val periodValue: String?
) : Parcelable
