package com.example.android.hive_assignment.model

import android.os.Parcelable
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.lang.reflect.Type
import java.util.*


@Entity
@Parcelize
data class Restaurant_(

        @Embedded
        @SerializedName("R")
        var r: R?,

        @ColumnInfo(name = "apikey")
        @SerializedName("apikey")
        var apikey: String?,

        @PrimaryKey
        @ColumnInfo(name = "id")
        @SerializedName("id")
        var id: String?,

        @ColumnInfo(name = "name")
        @SerializedName("name")
        var name: String?,

        @ColumnInfo(name = "url")
        @SerializedName("url")
        var url: String?,

        @Embedded
        @SerializedName("location")
        var location: Location?,

        @ColumnInfo(name = "switch_to_order_menu")
        @SerializedName("switch_to_order_menu")
        var switchToOrderMenu: Int?,

        @ColumnInfo(name = "cuisines")
        @SerializedName("cuisines")
        var cuisines: String?,

        @ColumnInfo(name = "timings")
        @SerializedName("timings")
        var timings: String?,

        @ColumnInfo(name = "average_cost_for_two")
        @SerializedName("average_cost_for_two")
        var averageCostForTwo: Int?,

        @ColumnInfo(name = "price_range")
        @SerializedName("price_range")
        var priceRange: Int?,

        @ColumnInfo(name = "currency")
        @SerializedName("currency")
        var currency: String?,

        @ColumnInfo(name = "highlights")
        @SerializedName("highlights")
        @TypeConverters(HighLightTypeConverter::class)
        var highlights: List<String?>,


        @ColumnInfo(name = "offers")
        @SerializedName("offers")
        @TypeConverters(OffersTypeConverter::class)
        var offers: @RawValue List<Any?>,

        @ColumnInfo(name = "opentable_support")
        @SerializedName("opentable_support")
        var opentableSupport: Int?,

        @ColumnInfo(name = "is_zomato_book_res")
        @SerializedName("is_zomato_book_res")
        var isZomatoBookRes: Int?,

        @ColumnInfo(name = "mezzo_provider")
        @SerializedName("mezzo_provider")
        var mezzoProvider: String?,

        @ColumnInfo(name = "is_book_form_web_view")
        @SerializedName("is_book_form_web_view")
        var isBookFormWebView: Int?,

        @ColumnInfo(name = "book_form_web_view_url")
        @SerializedName("book_form_web_view_url")
        var bookFormWebViewUrl: String?,

        @ColumnInfo(name = "book_again_url")
        @SerializedName("book_again_url")
        var bookAgainUrl: String?,

        @ColumnInfo(name = "thumb")
        @SerializedName("thumb")
        var thumb: String?,

        @Embedded
        @SerializedName("user_rating")
        var userRating: UserRating?,

        @ColumnInfo(name = "all_reviews_count")
        @SerializedName("all_reviews_count")
        var allReviewsCount: Int?,

        @ColumnInfo(name = "photos_url")
        @SerializedName("photos_url")
        var photosUrl: String?,

        @ColumnInfo(name = "photo_count")
        @SerializedName("photo_count")
        var photoCount: Int?,

        @ColumnInfo(name = "menu_url")
        @SerializedName("menu_url")
        var menuUrl: String?,

        @ColumnInfo(name = "featured_image")
        @SerializedName("featured_image")
        var featuredImage: String?,

        @ColumnInfo(name = "has_online_delivery")
        @SerializedName("has_online_delivery")
        var hasOnlineDelivery: Int?,

        @ColumnInfo(name = "is_delivering_now")
        @SerializedName("is_delivering_now")
        var isDeliveringNow: Int?,

        @ColumnInfo(name = "store_type")
        @SerializedName("store_type")
        var storeType: String?,

        @ColumnInfo(name = "include_bogo_offers")
        @SerializedName("include_bogo_offers")
        var includeBogoOffers: Boolean?,

        @ColumnInfo(name = "deeplink")
        @SerializedName("deeplink")
        var deeplink: String?,

        @ColumnInfo(name = "is_table_reservation_supported")
        @SerializedName("is_table_reservation_supported")
        var isTableReservationSupported: Int?,

        @ColumnInfo(name = "has_table_booking")
        @SerializedName("has_table_booking")
        var hasTableBooking: Int?,

        @ColumnInfo(name = "events_url")
        @SerializedName("events_url")
        var eventsUrl: String?,

        @ColumnInfo(name = "phone_numbers")
        @SerializedName("phone_numbers")
        var phoneNumbers: String?,

        @Embedded
        @SerializedName("all_reviews")
        var allReviews: AllReviews?,

        @ColumnInfo(name = "establishment")
        @SerializedName("establishment")
        @TypeConverters(OffersTypeConverter::class)
        var establishment: @RawValue List<Any?>?,

        @ColumnInfo(name = "establishment_types")
        @SerializedName("establishment_types")
        @TypeConverters(OffersTypeConverter::class)
        var establishmentTypes: @RawValue List<Any?>?
) : Parcelable


class HighLightTypeConverter {
    val gson = Gson()

    @TypeConverter
    fun stringToList(data: String?): List<String> {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object : TypeToken<List<String?>?>() {}.type
        return gson.fromJson<List<String>>(data, listType)
    }

    @TypeConverter
    fun ListToString(someObjects: List<String?>?): String {
        return gson.toJson(someObjects)
    }
}


class OffersTypeConverter {
    val gson = Gson()

    @TypeConverter
    fun stringToList(data: String?): List<Any?> {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object : TypeToken<List<Any?>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun ListToString(someObjects: List<Any?>): String {
        return gson.toJson(someObjects)
    }
}