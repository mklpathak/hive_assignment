package com.example.android.hive_assignment.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Entity
@Parcelize
data class Location(

        @ColumnInfo(name = "address")
        @SerializedName("address")
        var address: String?,

        @ColumnInfo(name = "locality")
        @SerializedName("locality")
        var locality: String?,

        @ColumnInfo(name = "city")
        @SerializedName("city")
        var city: String?,

        @ColumnInfo(name = "city_id")
        @SerializedName("city_id")
        var cityId: Int?,


        @ColumnInfo(name = "latitude")
        @SerializedName("latitude")
        var latitude: String?,

        @ColumnInfo(name = "longitude")
        @SerializedName("longitude")
        var longitude: String?,

        @ColumnInfo(name = "zipcode")
        @SerializedName("zipcode")
        var zipcode: String?,

        @ColumnInfo(name = "country_id")
        @SerializedName("country_id")
        var countryId: Int?,

        @ColumnInfo(name = "locality_verbose")
        @SerializedName("locality_verbose")
        var localityVerbose: String?) : Serializable, Parcelable