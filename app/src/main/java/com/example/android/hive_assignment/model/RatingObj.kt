package com.example.android.hive_assignment.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class RatingObj(
        @Embedded
        @SerializedName("title")
        var title: Title,

        @Embedded
        @SerializedName("bg_color")
        var bgColor: BgColor?
) : Parcelable