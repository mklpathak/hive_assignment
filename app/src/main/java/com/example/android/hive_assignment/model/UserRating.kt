package com.example.android.hive_assignment.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class UserRating(
        @ColumnInfo(name = "aggregate_rating")
        @SerializedName("aggregate_rating")
        val aggregateRating: Double?,
        @ColumnInfo(name = "rating_text")
        @SerializedName("rating_text")
        val ratingText: String?,
        @ColumnInfo(name = "rating_color")
        @SerializedName("rating_color")
        val ratingColor: String?,
        @Embedded
        @SerializedName("rating_obj")
        val ratingObj: RatingObj?,
        @ColumnInfo(name = "votes")
        @SerializedName("votes")
        val votes: Int) : Parcelable