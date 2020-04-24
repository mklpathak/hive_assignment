package com.example.android.hive_assignment.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "offline_restraunts")
@Parcelize
data class Restaurant(

        @Embedded
        @SerializedName("restaurant")
        var restaurant: Restaurant_?) : Parcelable {

    @PrimaryKey(autoGenerate = true)
    var restraunt_uniqueId: Int = 0


}