package com.example.android.hive_assignment.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class Review(

        @SerializedName("review")
        var review: @RawValue List<Any?>) : Parcelable