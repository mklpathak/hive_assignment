package com.example.android.hive_assignment.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Title(
        @ColumnInfo(name = "text")
        @SerializedName("text")
        var text: String?) : Parcelable