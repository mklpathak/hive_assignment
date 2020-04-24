package com.example.android.hive_assignment.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ZomatoResponse(@SerializedName("results_found")
                          val resultsFound: Int?,

                          @SerializedName("results_start")
                          val resultsStart: Int?,

                          @SerializedName("results_shown")
                          val resultsShown: Int?,

                          @SerializedName("restaurants")
                          val restaurants: List<Restaurant?>) : Parcelable