package com.example.android.hive_assignment.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.Parcelize
import java.lang.reflect.Type
import java.util.*

@Entity
@Parcelize
data class AllReviews(
        @ColumnInfo(name = "reviews")
        @SerializedName("reviews")
        @TypeConverters(ReviewsTypeConverter::class)
        var reviews: List<Review?>) : Parcelable


class ReviewsTypeConverter {
    val gson = Gson()

    @TypeConverter
    fun stringToList(data: String?): List<Review?> {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object : TypeToken<List<Review?>?>() {}.type
        return gson.fromJson<List<Review?>>(data, listType)
    }

    @TypeConverter
    fun ListToString(someObjects: List<Review?>?): String {
        return gson.toJson(someObjects)
    }
}
