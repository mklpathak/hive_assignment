package com.example.android.hive_assignment.network

import com.example.android.hive_assignment.model.ZomatoResponse
import com.example.android.hive_assignment.zomato_key
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit


private const val BASE_URL = "https://developers.zomato.com/api/v2.1/"

enum class ZomatoFilters(val value: String) { RATINGS("rating"), PRICE("cost") }

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */
private val retrofit = Retrofit.Builder()
        .client(getUnsafeOkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(BASE_URL)
        .build()


fun getUnsafeOkHttpClient(): OkHttpClient {

    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    val httpClient = OkHttpClient.Builder()
    httpClient.addInterceptor { chain ->
        val original = chain.request()
        var builder = original.newBuilder()
        builder.addHeader("user-key", zomato_key)
        builder.addHeader("Content-Type", "application/json")
        chain.proceed(builder.build())
    }

    // httpClient.addInterceptor(interceptor)
    httpClient.connectTimeout(30, TimeUnit.SECONDS)
    httpClient.readTimeout(30, TimeUnit.SECONDS)
    return httpClient.build()
}


/**
 * A public interface that exposes the [getRestraunt] method
 */
interface ZomatoApiService {
    /**
     * Returns a Coroutine [Deferred] [List] of [Restaurants] which can be fetched with await() if
     * in a Coroutine scope.
     * The @GET annotation indicates that the "zomato" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("search")
    fun getRestraunt(@Query("q") type: String):
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
            Deferred<ZomatoResponse>

    @GET("search")
    fun getRestrauntBySorting(@Query("q") type: String, @Query("sort") sort: String, @Query("order") orderBy: String):
    // The Coroutine Call Adapter allows us to return a Deferred, a Job with a result
            Deferred<ZomatoResponse>
}


/**
 * A public Api object that exposes the lazy-initialized Retrofit service
 */
object ZomatoApi {
    val RETROFIT_SERVICE: ZomatoApiService by lazy { retrofit.create(ZomatoApiService::class.java) }
}
