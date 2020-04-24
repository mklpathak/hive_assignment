package com.example.android.hive_assignment.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class R(

        @Embedded
        @SerializedName("has_menu_status")
        var hasMenuStatus: HasMenuStatus?,

        @ColumnInfo(name = "res_id")
        @SerializedName("res_id")
        var resId: Int?
) : Parcelable