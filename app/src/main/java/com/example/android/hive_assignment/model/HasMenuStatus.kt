package com.example.android.hive_assignment.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class HasMenuStatus(

        @ColumnInfo(name = "delivery")
        @SerializedName("delivery")
        var delivery: Int?,

        @ColumnInfo(name = "takeaway")
        @SerializedName("takeaway")
        var takeaway: Int?) : Parcelable