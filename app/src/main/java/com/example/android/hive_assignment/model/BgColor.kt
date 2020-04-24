package com.example.android.hive_assignment.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class BgColor(
        @ColumnInfo(name = "type")
        @SerializedName("type")
        var type: String?,

        @ColumnInfo(name = "tint")
        @SerializedName("tint")
        var tint: String?) : Parcelable